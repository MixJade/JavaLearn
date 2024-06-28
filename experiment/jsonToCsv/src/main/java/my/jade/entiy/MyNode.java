package my.jade.entiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.jade.enums.NodeTpEnum;
import my.jade.utils.IdUtil;

@Getter
@Setter
@AllArgsConstructor
public class MyNode {
    /**
     * 节点编号
     */
    private Long nodeId;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 节点类型
     */
    private NodeTpEnum nodeType;

    public static MyNode newStar(String nodeName) {
        return new MyNode(IdUtil.getId(1), nodeName, NodeTpEnum.STAR);
    }

    public static MyNode newWork(String nodeName) {
        return new MyNode(IdUtil.getId(1), nodeName, NodeTpEnum.WORK);
    }
}
