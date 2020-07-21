package allthings.iot.das.mqtt.protocol.subscriptions;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class TreeNode {

    @SuppressWarnings("unused")
    private TreeNode parent;
    private Token token;
    private List<TreeNode> children = Collections.synchronizedList(new ArrayList<TreeNode>());
    private Cache<String, Subscription> subscriptionCache = CacheBuilder.newBuilder()
            .maximumSize(100000L).concurrencyLevel(16).build();

    protected TreeNode(TreeNode parent, Token token) {
        this.parent = parent;
        this.token = token;
    }

    protected void addSubscription(Subscription s) {
        subscriptionCache.put(getSubscriptionKey(s), s);
    }

    protected void addChild(TreeNode child) {
        children.add(child);
    }

    protected boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * Search for children that has the specified token, if not found return
     * null;
     */
    protected TreeNode childWithToken(Token token) {
        for (TreeNode child : children) {
            if (child.token.equals(token)) {
                return child;
            }
        }
        return null;
    }

    protected Cache<String, Subscription> getSubscriptionCache() {
        return subscriptionCache;
    }

    protected void matches(Queue<Token> tokens, List<Subscription> matchingSubs) {
        Token t = tokens.poll();
        //check if t is null <=> tokens finished
        if (t == null) {
            matchingSubs.addAll(subscriptionCache.asMap().values());
            //check if it has got a MULTI child and add its subscriptions
            for (TreeNode n : children) {
                if (n.token == Token.MULTI || n.token == Token.SINGLE) {
                    matchingSubs.addAll(n.getSubscriptionCache().asMap().values());
                }
            }
            return;
        }
        //we are on MULTI, than add subscriptions and return
        if (token == Token.MULTI) {
            matchingSubs.addAll(subscriptionCache.asMap().values());
            return;
        }
        for (TreeNode n : children) {
            if (n.token.match(t)) {
                //create a copy of token, or navigate to sibling it consumes 2 elements on the queue instead of one
                n.matches(new LinkedBlockingQueue<Token>(tokens), matchingSubs);
            }
        }
    }

    protected long size() {
        long res = subscriptionCache.size();
        for (TreeNode child : children) {
            res += child.size();
        }
        return res;
    }

    protected void removeClientSubscriptions(String clientId) {
        //collect what to delete and then delete to avoid ConcurrentModification
        List<Subscription> subsToRemove = new ArrayList<Subscription>();
        for (Subscription s : subscriptionCache.asMap().values()) {
            if (s.getClientId().equals(clientId)) {
                subsToRemove.add(s);
            }
        }
        for (Subscription s : subsToRemove) {
            subscriptionCache.invalidate(getSubscriptionKey(s));
        }
        //go deep
        for (TreeNode child : children) {
            child.removeClientSubscriptions(clientId);
        }
    }

    protected void deactivate(String clientId) {
        for (Subscription s : subscriptionCache.asMap().values()) {
            if (s.getClientId().equals(clientId)) {
                s.setActive(false);
            }
        }
        //go deep
        for (TreeNode child : children) {
            child.deactivate(clientId);
        }
    }

    protected void activate(String clientId) {
        for (Subscription s : subscriptionCache.asMap().values()) {
            if (s.getClientId().equals(clientId)) {
                s.setActive(true);
            }
        }
        //go deep
        for (TreeNode child : children) {
            child.activate(clientId);
        }
    }

    protected void removeSubscriptionByClientIdAndTopic(String clientId, String topic) {
        subscriptionCache.invalidate(getSubscriptionKey(clientId, topic));
    }

    private String getSubscriptionKey(String clientId, String topic) {
        return "c:" + clientId + "-t:" + topic;
    }

    private String getSubscriptionKey(Subscription subscription) {
        return getSubscriptionKey(subscription.getClientId(), subscription.getTopicFilter());
    }
}
