package leetCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 现有一个流程图，起始节点为z，结束节点为w。
 * 将图中节点x和节点y的连线断开
 * 请给出断开连线后可达的节点
 * <p>(只能单向运动。除了type为3的节点,其它节点需前置节点全部可达才可达)</p>
 * <pre>
 *      ┌─y──c──┐
 *   ┌─x┤       ├─m──w
 * z─┤  └─b─┐   │
 *   └─a──d─┴─e─┘
 *   流程图示例
 * </pre>
 * 期望：z,x,a,b,d,e
 */
public class CutFlowChart {
    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<>();
        // 1.节点列表
        nodeList.add(new Node("z", 1));
        nodeList.add(new Node("x", 1));
        nodeList.add(new Node("y", 4));
        nodeList.add(new Node("a", 1));
        nodeList.add(new Node("b", 1));
        nodeList.add(new Node("c", 4));
        nodeList.add(new Node("d", 1));
        nodeList.add(new Node("e", 1));
        nodeList.add(new Node("m", 2));
        nodeList.add(new Node("w", 2));
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
        Set<String> goodNodeNames = packFlowNode(beginNode, lineList);
        // 查询哪些节点有用
        List<Node> goodNode = new ArrayList<>();
        for (Node node : nodeList) {
            if (goodNodeNames.contains(node.name())) goodNode.add(node);
        }

        // 再进行第二次筛选，筛选并入点(有多个路径并入的点)
        List<Node> goodNode2 = secondCut(beginNode, lineList, goodNodeNames, goodNode);
        System.out.println("最终节点");
        System.out.println(goodNode2);
        for (Node node : goodNode2) {
            System.out.print(node.name() + ",");
        }
    }

    private static List<Node> secondCut(String begin, List<Line> lineList, Set<String> goodNodeNames, List<Node> goodNode) {
        // (一个节点需前置节点都可达才可达,但包含节点例外)
        // 将Line转为map(找初次路径上的所有并入点)
        Map<String, List<Line>> fromLineMap = lineList.stream()
                .filter(i -> goodNodeNames.contains(i.toNode()))
                .collect(Collectors.groupingBy(Line::toNode))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // 筛选前置节点未全部满足的并入点
        List<Node> nedDelNode = new ArrayList<>();
        for (Node node : goodNode) {
            // 类型为3的节点不用管并入筛选
            if (node.type() == 3) continue;
            // 如果是并入点就需要筛选
            if (fromLineMap.containsKey(node.name())) {
                List<Line> lines = fromLineMap.get(node.name());
                boolean nedDel = false;
                for (Line line : lines) {
                    if (!goodNodeNames.contains(line.fromNode())) {
                        nedDel = true;
                        break;
                    }
                }
                // 存在不可达的源点，则删除该并入点
                if (nedDel) nedDelNode.add(node);
            }
        }
        // 最后清理一波并入点，再筛选一次路径
        goodNode.removeAll(nedDelNode);
        List<String> goodNodeName2 = goodNode.stream()
                .map(Node::name)
                .toList();
        List<Line> lines2 = lineList.stream()
                .filter(i -> goodNodeName2.contains(i.fromNode()))
                .toList();
        Set<String> packFlowNode = packFlowNode(begin, lines2);
        // 查询哪些节点有用
        List<Node> goodNode2 = new ArrayList<>();
        for (Node node : goodNode) {
            if (packFlowNode.contains(node.name())) goodNode2.add(node);
        }
        return goodNode2;
    }

    private static Set<String> packFlowNode(String begin, List<Line> lineList) {
        // 注意：LinkedHashSet是能保持元素顺序的Set
        Set<String> goodNodeNames = new LinkedHashSet<>();
        // 将Line转为map
        Map<String, List<Line>> fromLineMap = lineList.stream()
                .collect(Collectors.groupingBy(Line::fromNode));
        // 从开始节点连线
        setNextNode(begin, goodNodeNames, fromLineMap);
        // 输出
        return goodNodeNames;
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
 * @param type 节点类型,1正常 2不展示 3并入时可多选一 4需要去除
 */
record Node(String name, int type) {
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
