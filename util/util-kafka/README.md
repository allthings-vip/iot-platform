
KAFKA 安全认证配置


一、server.properties 配置

listeners=SASL_PLAINTEXT://localhost:9092
advertised.listeners=SASL_PLAINTEXT://localhost:9092
advertised.host.name=localhost
advertised.port=9092

security.inter.broker.protocol=SASL_PLAINTEXT
sasl.mechanism.inter.broker.protocol=PLAIN
sasl.enabled.mechanisms=PLAIN
authorizer.class.name = kafka.security.auth.SimpleAcl@authorizer
super.users=User:admin

二、 broker 启动：

JAAS配置文件位置作为JVM参数传递给每个broker代理

kafka-run-class.sh 中加上

KAFKA_SASL_OPTS='-Djava.security.auth.login.config=/usr/local/kafka/config/kafka_server_jaas.conf'



三、 client 配置

prodcer  consumer 的 propertites 中增加：
put("security.protocol", "SASL_PLAINTEXT");
put("sasl.mechanism", "PLAIN");

启动时环境参数
    -Djava.security.auth.login.config=/etc/kafka/kafka_client_jaas.conf