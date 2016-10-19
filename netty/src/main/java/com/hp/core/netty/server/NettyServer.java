/**
 * 
 */
package com.hp.core.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.enums.SerializationTypeEnum;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author huangping 2016年7月24日 下午10:07:12
 */
public class NettyServer implements Server {

	private static Logger log = LoggerFactory.getLogger(NettyServer.class);

	private Channel channel;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	private int port; // 端口
	
	private SerializationTypeEnum serializationTypeEnum = SerializationTypeEnum.PROTOSTUFF; // 序列化方式
	
	//private 
	
	public NettyServer() {}
	
	/**
	 * @param port
	 */
	public NettyServer(int port) {
		this.port = port;
	}

	/**
	 * @param port
	 * @param serializationTypeEnum
	 */
	public NettyServer(int port, SerializationTypeEnum serializationTypeEnum) {
		this.serializationTypeEnum = serializationTypeEnum;
		this.port = port;
	}
	
	/**
	 * @param serializationTypeEnum
	 */
	public NettyServer(SerializationTypeEnum serializationTypeEnum) {
		this.serializationTypeEnum = serializationTypeEnum;
	}
	
	public void start() throws Exception {
		start(port);
	}

	@Override
	public void start(int port) throws Exception {
		log.info("开始监听端口：{}", port);
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(10);
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new NettyServerChannelInitialier(serializationTypeEnum));
		serverBootstrap.bind(port).sync();
		log.info("监听端口【{}】完成", port);
	}

	@Override
	public void stop() {
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

}
