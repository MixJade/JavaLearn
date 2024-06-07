package leetCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 破碎节点连线
 * 有一个流程图，它的一些连线上的节点被扣掉了
 * 现在需要
 * 1.重新连线(要求把破碎的线接上)
 * 2.并输出破碎节点的顺序
 * <pre>
 *      ┌─ ──c──┐
 *   ┌─x┤       ├─m
 * z─┤  └─ ─┐   │
 *   └─ ──d─┴─e─┘
 *   流程图示例
 * </pre>
 */
public class CutFlowLine {
    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<>();
        // 1.节点列表
        nodeList.add(new Node(1, "z", 0));
        nodeList.add(new Node(2, "x", 1));
        nodeList.add(new Node(7, "d", 1));
        nodeList.add(new Node(6, "c", 4));
        nodeList.add(new Node(8, "e", 1));
        nodeList.add(new Node(9, "m", 2));
        // 2.连线列表
        List<Line> lineList = CutFlowChart.getLineList();
        // 先将List<Line>变成Map<fromNode,List<toNode>>
        Map<Integer, List<Integer>> lineMap = lineList.stream()
                .collect(Collectors.groupingBy(Line::fromNodeId,
                        Collectors.mapping(Line::toNodeId, Collectors.toList())));
        CutFlowLine cutFlowLine = new CutFlowLine();
        List<Line> newLine = cutFlowLine.setNewLine(nodeList, lineMap);
        // 查看结果
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (Node node : nodeList) {
            nodeMap.put(node.nodeId(), node);
        }
        for (Line line : newLine) {
            System.out.printf("form: %s to: %s\n",
                    nodeMap.get(line.fromNodeId()).name(),
                    nodeMap.get(line.toNodeId()).name());
        }
        // 通过连线结果对节点进行排序
        List<Node> newNodeList = cutFlowLine.sortNodeByLine(nodeMap, lineList, lineMap);
        if (newNodeList == null) {
            System.out.println("排序未生效");
        } else {
            nodeList = newNodeList;
        }
        System.out.println("最终排序结果：");
        nodeList.forEach(i -> System.out.print(i.name() + ","));
    }

    /**
     * 对破碎节点重新连线
     *
     * @param nodeList 被扣掉后的剩余节点
     * @param lineMap  Map<fromNode,List<toNode>>
     * @return 新的连线集合
     */
    private List<Line> setNewLine(List<Node> nodeList, Map<Integer, List<Integer>> lineMap) {
        List<Line> newLineList = new ArrayList<>();
        // 从传来的节点列表提取出节点编号
        List<Integer> nodeIdList = nodeList.stream()
                .map(Node::nodeId)
                .toList();
        // 对每一个节点遍历查看其下一个节点是否存在
        for (Integer nodeId : nodeIdList) {
            // 查看是否有以当前节点为源点的路线
            if (lineMap.containsKey(nodeId)) {
                List<Integer> toNodeIdList = lineMap.get(nodeId);
                boolean hasRel = false;
                // 查询当前节点的下一节点是否存在
                for (Integer nextNodeId : toNodeIdList) {
                    if (nodeIdList.contains(nextNodeId)) {
                        // 存在则加入
                        newLineList.add(new Line(nodeId, nextNodeId, true));
                        hasRel = true;
                    }
                }
                // 不存在就找下一层
                while (!hasRel) {
                    List<Integer> nextLines = new ArrayList<>();
                    Set<Integer> nextNodeIdSet = new HashSet<>(toNodeIdList);
                    for (Integer aNodeId : nextNodeIdSet) {
                        if (lineMap.containsKey(aNodeId)) {
                            nextLines.addAll(lineMap.get(aNodeId));
                        }
                    }
                    if (nextLines.size() == 0) break; // 最后一个节点就退出
                    // 查询当前节点的下一节点是否存在
                    for (Integer nextNodeId : toNodeIdList) {
                        if (nodeIdList.contains(nextNodeId)) {
                            // 存在则加入
                            newLineList.add(new Line(nodeId, nextNodeId, true));
                            hasRel = true;
                        }
                    }
                    if (!hasRel) toNodeIdList = nextLines;
                }
            }
        }
        return newLineList;
    }

    /**
     * 对破碎节点进行层次遍历
     *
     * @param nodeMap  Map<nodeId,Node>被扣掉后的剩余节点
     * @param lineList 连线集合(旧)
     * @param lineMap  Map<fromNode,List<toNode>>
     * @return 按顺序排好的节点列表
     */
    private List<Node> sortNodeByLine(Map<Integer, Node> nodeMap, List<Line> lineList, Map<Integer, List<Integer>> lineMap) {
        // 淘换连线集合找出发节点
        Set<Integer> fromNodeIdSet = lineList.stream().map(Line::fromNodeId).collect(Collectors.toSet());
        Set<Integer> toNodeIdSet = lineList.stream().map(Line::toNodeId).collect(Collectors.toSet());
        fromNodeIdSet.removeAll(toNodeIdSet);
        // 没有开始节点就直接返回
        if (fromNodeIdSet.size() == 0) return null;
        // 开始节点有这些
        System.out.println("开始节点" + fromNodeIdSet);
        // 从开始节点进行遍历
        // 【注意：TreeMap是有序集合，里面的内容会按照key值进行排序】
        Map<Integer, Set<Integer>> sortMap = new TreeMap<>();
        for (int i = 0; i <= lineList.size(); i++) {
            sortMap.put(i, fromNodeIdSet);
            Set<Integer> nextIdSet = new HashSet<>();
            for (Integer fromId : fromNodeIdSet) {
                if (lineMap.containsKey(fromId))
                    nextIdSet.addAll(lineMap.get(fromId));
            }
            // 如果下层没有节点，就结束遍历
            if (nextIdSet.size() == 0) break;
            // 否则开始新的循环
            fromNodeIdSet = nextIdSet;
        }
        System.out.println("排序的层次(原版)：" + sortMap);
        Set<Integer> sortSet = new LinkedHashSet<>();
        for (Set<Integer> mapVal : sortMap.values()) {
            sortSet.addAll(mapVal);
        }
        System.out.println("最终节点顺序：" + sortSet);
        // 将节点取出来
        List<Node> newNodeList = new ArrayList<>();
        for (Integer sortId : sortSet) {
            if (nodeMap.containsKey(sortId))
                newNodeList.add(nodeMap.get(sortId));
        }
        return newNodeList;
    }
}
