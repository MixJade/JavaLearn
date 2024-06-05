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
    static List<Line> getLineList() {
        List<Line> lineList = new ArrayList<>();
        lineList.add(new Line(1, 2, true));
        lineList.add(new Line(1, 4, true));
        lineList.add(new Line(2, 3, false)); // 断开x到y的连接
        lineList.add(new Line(2, 5, true));
        lineList.add(new Line(3, 6, true));
        lineList.add(new Line(4, 7, true));
        lineList.add(new Line(7, 8, true));
        lineList.add(new Line(8, 9, true));
        lineList.add(new Line(5, 8, true));
        lineList.add(new Line(6, 9, true));
        lineList.add(new Line(9, 10, true));
        return lineList;
    }

    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<>();
        // 1.节点列表
        nodeList.add(new Node(1, "z", 0));
        nodeList.add(new Node(2, "x", 1));
        nodeList.add(new Node(3, "y", 4));
        nodeList.add(new Node(4, "a", 1));
        nodeList.add(new Node(5, "b", 1));
        nodeList.add(new Node(6, "c", 4));
        nodeList.add(new Node(7, "d", 1));
        nodeList.add(new Node(8, "e", 1));
        nodeList.add(new Node(9, "m", 2)); // 可以改成3
        nodeList.add(new Node(10, "w", 2));
        // 2.连线列表
        List<Line> lineList = getLineList();
        // 正式开始
        CutFlowChart cutFlowChart = new CutFlowChart();
        cutFlowChart.beginFlowCut(nodeList, lineList);
    }

    /**
     * 开始方法
     *
     * @param nodeList 节点列表
     * @param lineList 连线列表
     */
    private void beginFlowCut(List<Node> nodeList, List<Line> lineList) {
        // 指定开始节点(这大概率已知)
        Integer beginNodeId = null;
        for (Node node : nodeList) {
            if (node.type() == 0) {
                beginNodeId = node.nodeId();
                break;
            }
        }
        if (beginNodeId == null) {
            System.out.println("初始节点未设置");
            return;
        }
        // 组装流程数据
        Set<Integer> goodNodeIds = packFlowNode(beginNodeId, lineList);
        // 查询哪些节点有用
        List<Node> goodNode = new ArrayList<>();
        for (Node node : nodeList) {
            if (goodNodeIds.contains(node.nodeId())) goodNode.add(node);
        }

        // 再进行第二次筛选，筛选并入点(有多个路径并入的点)
        List<Node> goodNode2 = secondCut(beginNodeId, lineList, goodNodeIds, goodNode);
        System.out.println("最终节点");
        System.out.println(goodNode2);
        for (Node node : goodNode2) {
            System.out.print(node.name() + ",");
        }
    }

    /**
     * 第二次剪枝
     * 减去前置节点未全部满足的并入点(type3节点例外)
     *
     * @param begin       开始节点名称
     * @param lineList    连线列表(原版未动)
     * @param goodNodeIds 第一次筛选后的节点名称列表
     * @param goodNode    第一次筛选后的节点列表
     * @return 最终的节点列表
     */
    private List<Node> secondCut(Integer begin, List<Line> lineList, Set<Integer> goodNodeIds, List<Node> goodNode) {
        // (一个节点需前置节点都可达才可达,但包含节点例外)
        // 将Line转为map(找初次路径上的所有并入点)
        Map<Integer, List<Line>> fromLineMap = lineList.stream()
                .filter(i -> goodNodeIds.contains(i.toNodeId()))
                .collect(Collectors.groupingBy(Line::toNodeId))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // 没有并入点就不处理
        if (fromLineMap.keySet().size() == 0) return goodNode;
        // 筛选前置节点未全部满足的并入点
        List<Node> nedDelNode = new ArrayList<>();
        for (Node node : goodNode) {
            // 类型为3的节点不用管并入筛选
            if (node.type() == 3) continue;
            // 如果是并入点就需要筛选
            if (fromLineMap.containsKey(node.nodeId())) {
                List<Line> lines = fromLineMap.get(node.nodeId());
                boolean nedDel = false;
                for (Line line : lines) {
                    if (!goodNodeIds.contains(line.fromNodeId())) {
                        nedDel = true;
                        break;
                    }
                }
                // 存在不可达的源点，则删除该并入点
                if (nedDel) nedDelNode.add(node);
            }
        }
        if (nedDelNode.size() == 0) return goodNode;
        // 最后清理一波并入点，再筛选一次路径
        goodNode.removeAll(nedDelNode);
        List<Integer> goodNodeIds2 = goodNode.stream()
                .filter(i -> {
                    for (Node node : nedDelNode) {
                        if (node.nodeId().equals(i.nodeId()))
                            return false;
                    }
                    return true;
                })
                .map(Node::nodeId)
                .toList();
        List<Line> lines2 = lineList.stream()
                .filter(i -> goodNodeIds2.contains(i.fromNodeId()))
                .toList();
        Set<Integer> packFlowNode = packFlowNode(begin, lines2);
        // 查询哪些节点有用
        List<Node> goodNode2 = new ArrayList<>();
        for (Node node : goodNode) {
            if (packFlowNode.contains(node.nodeId())) goodNode2.add(node);
        }
        return goodNode2;
    }

    /**
     * 组装可以连起来的线,返回可达节点
     *
     * @param begin    开始节点编号
     * @param lineList 节点列表(第一次是原版，第二次是筛选过的)
     * @return 可以连成线的节点名称
     */
    private Set<Integer> packFlowNode(Integer begin, List<Line> lineList) {
        // 注意：LinkedHashSet是能保持元素顺序的Set
        Set<Integer> goodNodeIds = new LinkedHashSet<>();
        // 将Line转为map
        Map<Integer, List<Line>> fromLineMap = lineList.stream()
                .collect(Collectors.groupingBy(Line::fromNodeId));
        // 从开始节点连线
        setNextNode(begin, goodNodeIds, fromLineMap);
        // 输出
        return goodNodeIds;
    }

    /**
     * (递归方法)遍历寻找每个节点的下一个节点
     *
     * @param begin       当前节点
     * @param alreadyNode 已找到的节点列表,用于登记
     * @param fromLineMap 以源点为key的路线映射
     */
    private void setNextNode(Integer begin, Set<Integer> alreadyNode, Map<Integer, List<Line>> fromLineMap) {
        alreadyNode.add(begin);
        // 最后一个节点直接返回
        if (!fromLineMap.containsKey(begin)) return;
        // 正常连线
        List<Line> nextLines = fromLineMap.get(begin);
        for (Line nextLine : nextLines) {
            if (nextLine.ok()) {
                setNextNode(nextLine.toNodeId(), alreadyNode, fromLineMap);
            }
        }
    }
}

/**
 * 节点对象
 *
 * @param nodeId 节点编号
 * @param name   节点名称
 * @param type   节点类型,0开始 1正常 2不展示 3并入时可多选一 4需要去除
 */
record Node(Integer nodeId, String name, int type) {
}

/**
 * 连线对象
 *
 * @param fromNodeId 源点编号
 * @param toNodeId   目标点编号
 * @param ok         是否可用
 */
record Line(Integer fromNodeId, Integer toNodeId, boolean ok) {
}
