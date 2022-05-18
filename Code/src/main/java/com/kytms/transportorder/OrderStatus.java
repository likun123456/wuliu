package com.kytms.transportorder;

/**
 * 奇趣源码商城 www.qiqucode.com
 * <p>
 * 订单状态
 *
 * @author 奇趣源码
 * @create 2018-01-15
 */

public enum OrderStatus {
    UNACTIVE(0),//失效
    ACTIVE(1), //生效
    CONFIGRM(2), //提交
    STOWAGE(3), //配载
    DESPATCH(4),//发运
    ARRIVE(5),//运抵
    UNABSORBED(12),//未接收
    RECEIVE(11),//接收
    REFUSERECEIVE(19),//拒收
    SEND_CAR(13), //已派车
    NOT_BACK(14),//无回单
    NOT_SING(15),//未签收
    END_SING(16),//已经签收
    SING_EXCEPTION(17),//签收异常
    BACK(18),//回单
    BACK_SEED(20),//回单邮寄
    SUBMIT(19),//交单
    END(99);//完结


    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
