package com.quaero.qbcTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataPackageHead {
    /**
     * 协议标识
     * 0xA55A
     */
    public byte[] Mark;
    /**
     * 命令
     */
    public int Command;
    /**
     *  包体长度
     */
    public int BodyLen;
    /**
     * 包体校验和
     */
    public int BodySum;
    /**
     * 包头校验和
     */
    public int HeadSum;

}
