package allthings.iot.das.mqtt.protocol.subscriptions;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author :  sylar
 * @FileName :  MqttConst
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class SubscriptionsStore {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionsStore.class);
    private static final String TOPIC_SPLIT_STRING = "/";

    private TreeNode subscriptions = new TreeNode(null, null);

    public void add(Subscription newSubscription) {
        addDirect(newSubscription);
    }

    private void addDirect(Subscription newSubscription) {
        TreeNode current = findMatchingNode(newSubscription.getTopicFilter());
        current.addSubscription(newSubscription);
    }

    private TreeNode findMatchingNode(String topic) {
        List<Token> tokens = new ArrayList<Token>();
        try {
            tokens = parseTopic(topic);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        TreeNode current = subscriptions;
        for (Token token : tokens) {
            TreeNode matchingChildren;
            //check if a children with the same token already exists
            if ((matchingChildren = current.childWithToken(token)) != null) {
                current = matchingChildren;
            } else {
                //create a new node for the newly inserted token
                matchingChildren = new TreeNode(current, token);
                current.addChild(matchingChildren);
                current = matchingChildren;
            }
        }
        return current;
    }

    public void removeSubscription(String topic, String clientId) {
        TreeNode matchNode = findMatchingNode(topic);
        matchNode.removeSubscriptionByClientIdAndTopic(clientId, topic);
    }

    public void removeForClient(String clientId) {
        subscriptions.removeClientSubscriptions(clientId);
        LOG.debug("remove subscriptions for clientId: {}", clientId);
    }

    public void deactivate(String clientId) {
        subscriptions.deactivate(clientId);
        LOG.debug("deactivate subscriptions for clientId: {}", clientId);
    }

    public void activate(String clientId) {
        subscriptions.activate(clientId);
        LOG.debug("activating subscriptions for clientId: {}", clientId);
    }

    /**
     * Given a topic string return the clients subscriptions that matches it.
     * Topic string can't contain character # and + because they are reserved to
     * listeners subscriptions, and not topic publishing.
     */
    public List<Subscription> matches(String topic) {
        List<Token> tokens;
        try {
            tokens = parseTopic(topic);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return Lists.newArrayList();
        }
        Queue<Token> tokenQueue = new LinkedBlockingDeque<Token>(tokens);
        List<Subscription> matchingSubs = new ArrayList<Subscription>();
        subscriptions.matches(tokenQueue, matchingSubs);
        return matchingSubs;
    }

    private List<Token> parseTopic(String topic) throws ParseException {
        List<Token> res = new ArrayList<Token>();
        if (Strings.isNullOrEmpty(topic)) {
            res.add(Token.EMPTY);
            return res;
        }
        String[] splitted = topic.split("/");
        for (int i = 0; i < splitted.length; i++) {
            String s = splitted[i];
            if (s.isEmpty()) {
                res.add(Token.EMPTY);
            } else if (s.equals(Token.MULTI.getName())) {
                //check that multi is the last symbol
                if (i != splitted.length - 1) {
                    throw new ParseException("Bad format of topic, the multi symbol (#) has to be the last one after " +
                            "a separator", i);
                }
                res.add(Token.MULTI);
            } else if (s.equals(Token.SINGLE.getName())) {
                res.add(Token.SINGLE);
            } else if (s.contains(Token.MULTI.getName()) || s.contains(Token.SINGLE.getName())) {
                throw new ParseException("Bad format of topic, invalid subtopic name: " + s, i);
            } else {
                res.add(new Token(s));
            }
        }
        if (topic.endsWith(TOPIC_SPLIT_STRING)) {
            //尾部增加一个空的token
            res.add(Token.EMPTY);
        }
        return res;
    }

    public long size() {
        return subscriptions.size();
    }

}
