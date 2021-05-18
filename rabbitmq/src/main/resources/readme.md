
使用docker运行rabbitmq
先执行下面的命令创建一个文件夹，用来存储持久化数据
```shell
mkdir -p /Users/yujunjie/tmp/rabbitmq/data
#-p表示如果不存在这个文件夹就创建
```
然后再执行下面的命令
```docker
docker run -d \
--name rabbitmq3.8.16 \
--hostname my-rabbit \
-p 5672:5672 \
-p 15672:15672 \
-v /Users/yujunjie/tmp/rabbitmq/data:/var/lib/rabbitmq \
-e RABBITMQ_DEFAULT_USER=admin \
-e RABBITMQ_DEFAULT_PASS=admin \
rabbitmq:3.8.16-management
```
然后访问127.0.0.1:15672出现登录界面表示部署成功，使用admin/admin登录

```yaml
spring:
  rabbitmq:
    listener:
      #      simple和direct这两项配置似乎不同
      # 经实践得到，simple模式的消息在调用监听器容器的线程上消费
      #           direct模式的消息在在RabbitMQ消费者线程上调用
      #      发送到交换机可配置direct或simple项，下面是一个例子，配置实现“发送完毕需要手动确认”
      #      使用哪一个，下面的type要变更成相应的类型simple/direct
      type: direct
#      simple:
#        retry:
#          enabled: true # 开启消费者进行重试
#          max-attempts: 3 #最大重试次数
#          initial-interval: 1500 # 重试时间间隔
#          max-interval: 5000
#        acknowledge-mode: manual # 手动签收模式
#        prefetch: 5 # 每次签收一条消息
#        default-requeue-rejected: true
      direct:
        retry:
          enabled: true # 开启消费者进行重试
          max-attempts: 3 #最大重试次数
          initial-interval: 1500 # 重试时间间隔
          max-interval: 5000
        acknowledge-mode: manual # 手动签收模式
        prefetch: 3 # 每个消费者未确认的最大消息数，如果是1有一个消息未确认，后面的消息就不会再继续消费，如果是3则表示未确认消息数量在未达到3之前可以继续消费
        default-requeue-rejected: true


    host: 127.0.0.1
    port: 5672
    publisher-confirm-type: correlated # 开启消息确认机制
    publisher-returns: true # 消息在未被队列收到的情况下返回
#    virtual-host: /
#    username: guest
#    password: guest
server:
  port: 8080
```

在[RabbitConfig](com.test.rabbit.config.RabbitConfig)中声明了3个Exchange交换机
分别是direct、fanout类型，还有一个用于死信队列的交换机，以及绑定的队列

ConfirmProviderExt中什么发送的消息
ConfirmConsumerExt中接收不同的消息

fanout模式只要交互机绑定了几个队列，发送消息后就会有几个队列收到消息，可以用于多个系统间进行数据对接，每个系统监听不同的队列




