package my.jade.entiy;

import my.jade.utils.IdUtil;

/**
 * @param lineId     连线编号
 * @param formNodeId 源节点
 * @param toNodeId   目标节点
 */
public record MyLine(Long lineId, Long formNodeId, Long toNodeId) {
    public static MyLine bu(Long formNodeId, Long toNodeId) {
        return new MyLine(IdUtil.getId(2), formNodeId, toNodeId);
    }
}
