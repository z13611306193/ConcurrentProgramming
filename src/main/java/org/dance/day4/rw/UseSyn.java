package org.dance.day4.rw;


import org.dance.tools.SleepTools;

/**
 * 类说明：用内置锁来实现商品服务接口
 */
public class UseSyn implements GoodsService {

	private GoodsInfo goodsInfo;

	public UseSyn(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	@Override
	public synchronized GoodsInfo getNum() {
		SleepTools.ms(5);
		return this.goodsInfo;
	}

	@Override
	public synchronized void setNum(int number) {
		SleepTools.ms(5);
		goodsInfo.changeNumber(number);

	}

}
