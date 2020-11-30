package com.sailmi.message.core.service;

import com.sailmi.message.core.config.ChannelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class ChannelSMSServices implements Iterable<IChannelService> {

	@Autowired
	private ChannelConfig channelConfig;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Iterator<IChannelService> iterator() {
		return new Itr();
	}

	public IChannelService getBatchSendable() {
		return (IChannelService) applicationContext.getBean(channelConfig.getBatchSend());
	}

	private class Itr implements Iterator<IChannelService> {

		int i;

		@Override
		public boolean hasNext() {
			return i < channelConfig.getAvailable().size();
		}

		@Override
		public IChannelService next() {
			return (IChannelService) applicationContext.getBean(channelConfig.getAvailable().get(i++));
		}
	}

}
