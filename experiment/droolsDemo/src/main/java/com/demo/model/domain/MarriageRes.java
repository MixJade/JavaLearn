package com.demo.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "相亲结果返回类")
public class MarriageRes {
    @Schema(description = "相亲是否成功")
    boolean marriageSuc;

    @Schema(description = "女方说的话")
    String girlSay;

    @Schema(description = "彩礼")
    long payMoney;

    @Schema(description = "媒婆说的话")
    String agencySay;

    @Schema(description = "媒婆收的钱")
    long agencyPay;
}
