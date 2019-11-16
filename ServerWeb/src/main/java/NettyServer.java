import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class NettyServer {
    final static int port = 8080;

    public static void main(String[] args) {
        Server server = new Server();
        server.config(port);
        server.start();
    }
}

class Server {
    ServerBootstrap bootstrap;
    Channel parentChannel;
    InetSocketAddress localAddress;
    MyChannelHandler channelHandler = new MyChannelHandler();

    Server() {
        //初始化Server，并注入NioServerSocketChannelFactory，用于创建通道，再建立两个线程池，第一个用来接收连接，第二个用来接收消息
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        //设置通道和子通道相关的属性
        bootstrap.setOption("reuseAddress", true);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.soLinger", 2);

        //向ChannelPipeline中添加处理器，用于处理连接和消息
        ChannelPipeline pipeline=bootstrap.getPipeline();
        //pipeline.addLast("encode", new StringEncoder());
        //pipeline.addLast("decode", new StringDecoder());
        pipeline.addLast("servercnfactory", channelHandler);

    }

    void config(int port) {
        this.localAddress = new InetSocketAddress(port);
    }

    void start() {
        parentChannel = bootstrap.bind(localAddress);
    }

    //channelHandler的实现
    class MyChannelHandler extends SimpleChannelHandler {

        @Override
        public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
                throws Exception {
            System.out.println("Channel closed " + e);
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx,
                                     ChannelStateEvent e) throws Exception {
            System.out.println("Channel connected " + e);
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
                throws Exception {
            try {
                ChannelBuffer recevie = (ChannelBuffer) e.getMessage();
                String msg = new String(recevie.array());
                System.out.println("New message " + msg + " from "
                        + ctx.getChannel());
                //把收到的消息写回给客户端
                processMessage(e);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            System.out.println(e.getCause());
        }

        private void processMessage(MessageEvent e) {
            Channel ch = e.getChannel();
            ch.write(e.getMessage());
        }
    }
}