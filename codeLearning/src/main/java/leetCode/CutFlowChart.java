package leetCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 现有一个流程图，起始节点为z，结束节点为w。
 * 将图中节点x和节点y的连线断开
 * 请给出断开连线后可达的节点
 * (只能单向运动)
 * <pre>
 *      ┌─y──c──┐
 *   ┌─x┤       ├─m──w
 * z─┤  └─b─┐   │
 *   └─a──d─┴─e─┘
 *   流程图示例
 * </pre>
 * 期望：z, x, b, e, m, w, a, d
 */
public class CutFlowChart {
    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<>();
        // 1.节点列表
        nodeList.add(new Node("z", "起始节点"));
        nodeList.add(new Node("x", "分支1"));
        nodeList.add(new Node("y", "弃用节点"));
        nodeList.add(new Node("a", "分支2"));
        nodeList.add(new Node("b", "分支1.1"));
        nodeList.add(new Node("c", "弃用节点"));
        nodeList.add(new Node("d", "分支2.1"));
        nodeList.add(new Node("e", "分支2.1.1"));
        nodeList.add(new Node("m", "末尾1"));
        nodeList.add(new Node("w", "末尾2"));
        // 2.连线列表
        List<Line> lineList = new ArrayList<>();
        lineList.add(new Line("z", "x", true));
        lineList.add(new Line("z", "a", true));
        lineList.add(new Line("x", "y", false)); // 断开x到y的连接
        lineList.add(new Line("x", "b", true));
        lineList.add(new Line("y", "c", true));
        lineList.add(new Line("a", "d", true));
        lineList.add(new Line("d", "e", true));
        lineList.add(new Line("e", "m", true));
        lineList.add(new Line("b", "e", true));
        lineList.add(new Line("c", "m", true));
        lineList.add(new Line("m", "w", true));
        // 指定开始节点(这大概率已知)
        String beginNode = "z";
        // 组装流程数据
        Set<String> alreadyNode = packFlowNode(beginNode, lineList);
        // 查询哪些节点有用
        List<Node> goodNode = new ArrayList<>();
        for (Node node : nodeList) {
            if (alreadyNode.contains(node.name())) goodNode.add(node);
        }
        System.out.println("最终可用节点：");
        System.out.println(goodNode);
    }

    private static Set<String> packFlowNode(String begin, List<Line> lineList) {
        // 注意：LinkedHashSet是能保持元素顺序的Set
        Set<String> alreadyNode = new LinkedHashSet<>();
        // 将Line转为map
        Map<String, List<Line>> fromLineMap = lineList.stream()
                .collect(Collectors.groupingBy(Line::fromNode));
        // 从开始节点连线
        setNextNode(begin, alreadyNode, fromLineMap);
        // 输出
        System.out.println(alreadyNode);
        return alreadyNode;
    }

    private static void setNextNode(String begin, Set<String> alreadyNode, Map<String, List<Line>> fromLineMap) {
        alreadyNode.add(begin);
        // 最后一个节点直接返回
        if (!fromLineMap.containsKey(begin)) return;
        // 正常连线
        List<Line> nextLines = fromLineMap.get(begin);
        for (Line nextLine : nextLines) {
            if (nextLine.ok()) {
                setNextNode(nextLine.toNode(), alreadyNode, fromLineMap);
            }
        }
    }
}

/**
 * 节点对象
 *
 * @param name 节点名称
 * @param msg  节点备注
 */
record Node(String name, String msg) {
}

/**
 * 连线对象
 *
 * @param fromNode 源点名称
 * @param toNode   目标点名称
 * @param ok       是否可用
 */
record Line(String fromNode, String toNode, boolean ok) {
}