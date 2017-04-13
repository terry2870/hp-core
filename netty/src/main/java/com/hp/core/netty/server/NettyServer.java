/**
 * 
 */
package com.hp.core.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.enums.SerializationTypeEnum;
import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.serialize.protostuff.ProtostuffDecoder;
import com.hp.core.netty.serialize.protostuff.ProtostuffEncoder;
import com.hp.core.netty.server.NettyServerChannelInboundHandler.NettyProcess;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author huangping 2016年7月24日 下午10:07:12
 */
public class NettyServer implements Server {

	private static Logger log = LoggerFactory.getLogger(NettyServer.class);

	private Channel channel;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ServerBootstrap serverBootstrap;
	private int threadSize = Runtime.getRuntime().availableProcessors() * 2; // 服务端处理的线程数。默认为cpu核数 * 2
	
	private int port; // 端口
	
	private SerializationTypeEnum serializationTypeEnum = SerializationTypeEnum.PROTOSTUFF; // 序列化方式
	
	// 服务端处理的具体方法
	private NettyProcess nettyProcess;
	
	public NettyServer() {}
	
	/**
	 * @param port
	 */
	public NettyServer(int port) {
		setPort(port);
	}

	/**
	 * @param port
	 * @param nettyProcess
	 */
	public NettyServer(int port, NettyProcess nettyProcess) {
		this(port);
		setNettyProcess(nettyProcess);
	}
	
	/**
	 * 
	 * @param port
	 * @param nettyProcess
	 * @param queueSize
	 */
	public NettyServer(int port, NettyProcess nettyProcess, int threadSize) {
		this(port, nettyProcess);
		setThreadSize(threadSize);
	}
	
	/**
	 * @param port
	 * @param nettyProcess
	 * @param serializationTypeEnum
	 *//*
	public NettyServer(int port, NettyProcess nettyProcess, int queueSize, SerializationTypeEnum serializationTypeEnum) {
		this(port, nettyProcess, queueSize);
		this.serializationTypeEnum = serializationTypeEnum;
	}
	
	*//**
	 * @param port
	 * @param nettyProcess
	 * @param serializationTypeEnum
	 *//*
	public NettyServer(int port, NettyProcess nettyProcess, int queueSize, String serializationTypeEnumName) {
		this(port, nettyProcess, queueSize, SerializationTypeEnum.getEnumByName(serializationTypeEnumName));
	}*/
	
	@Override
	public Server start() throws Exception {
		log.info("init server start with port={}", port);
		
		initServerBootstrap();
		
		log.info("init server success with port={}", port);
		return this;
	}
	
	/**
	 * 初始化serverBootstrap
	 * @throws Exception
	 */
	private void initServerBootstrap() throws Exception {
		log.info("initServerBootstrap server start with port={}", port);
		bossGroup = new NioEventLoopGroup(threadSize); //接收消息循环队列
		workerGroup = new NioEventLoopGroup(threadSize);//发送消息循环队列
		serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.option(ChannelOption.TCP_NODELAY, true)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					log.info("服务端初始化开始");
					ChannelPipeline pipeline = ch.pipeline();
					switch (serializationTypeEnum) {
					case PROTOSTUFF:
						pipeline.addLast(new ProtostuffDecoder(NettyRequest.class));
						pipeline.addLast(new ProtostuffEncoder(NettyResponse.class));
						break;
					case LINEBASED:
						pipeline.addLast(new LineBasedFrameDecoder(1024));
						pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
						break;
					default:
						pipeline.addLast(new ProtostuffEncoder(NettyRequest.class));
						pipeline.addLast(new ProtostuffDecoder(NettyResponse.class));
						break;
					}
					pipeline.addLast(new NettyServerChannelInboundHandler(nettyProcess));
				}
			});
		channel = serverBootstrap.bind(port).sync().channel();
		//channel.closeFuture().sync();
		log.info("initServerBootstrap server end with port={}", port);
	}

	@Override
	public Server stop() {
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
		if (channel != null) {
			channel.closeFuture().syncUninterruptibly();
		}
		bossGroup = null;
		workerGroup = null;
		channel = null;
		return this;
	}

	public SerializationTypeEnum getSerializationTypeEnum() {
		return serializationTypeEnum;
	}

	public void setSerializationTypeEnum(SerializationTypeEnum serializationTypeEnum) {
		this.serializationTypeEnum = serializationTypeEnum;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public NettyProcess getNettyProcess() {
		return nettyProcess;
	}

	public void setNettyProcess(NettyProcess nettyProcess) {
		this.nettyProcess = nettyProcess;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		if (threadSize == 0) {
			return;
		}
		this.threadSize = threadSize;
	}

}
