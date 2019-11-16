
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 *  Netty客户端入门第一个小程序
 */
public class NettyClient {
    public static void main(String[] args) {
        // 服务类
        ClientBootstrap bootstrap = new ClientBootstrap();

        // 线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService work = Executors.newCachedThreadPool();

        // 设置socketChannelFactory工厂
        bootstrap.setFactory(new NioClientSocketChannelFactory(boss,work));

        // 设置管道工厂
        bootstrap.setPipelineFactory(() -> {
            ChannelPipeline pipeline = Channels.pipeline();

            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            pipeline.addLast("clientHandler", new ClientChannelHandler());

            return pipeline;
        });

        // 连接服务器
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));

        // 得到channel
        Channel channel = connect.getChannel();

        System.out.println("连上服务器。。。");

        // 测试
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("请说话：");
            channel.write(scanner.nextLine());
        }
    }
}



/**
 * 客户端 ChannelHandler
 */

class ClientChannelHandler extends SimpleChannelHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
        System.out.println("==========messageReceived===============");
        System.out.println(e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
        System.out.println("=======exceptionCaught=======");
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
        System.out.println("=======channelConnected=======");
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        System.out.println("=======channelDisconnected=======");
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("=======channelClosed=======");
        super.channelClosed(ctx, e);
    }
}