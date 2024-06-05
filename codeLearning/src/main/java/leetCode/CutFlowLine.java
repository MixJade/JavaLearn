package leetCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 破碎节点连线
 * 有一个流程图，它的一些连线上的节点被扣掉了
 * 现在需要重新连线(要求把破碎的线接上)
 * <pre>
 *      ┌─ ── ──┐
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
        nodeList.add(new Node(8, "e", 1));
        nodeList.add(new Node(9, "m", 2));
        // 2.连线列表
        List<Line> lineList = new ArrayList<>();
        lineList.add(new Line(1, 2, true));
        lineList.add(new Line(1, 4, true));
        lineList.add(new Line(2, 3, true));
        lineList.add(new Line(2, 5, true));
        lineList.add(new Line(3, 6, true));
        lineList.add(new Line(4, 7, true));
        lineList.add(new Line(7, 8, true));
        lineList.add(new Line(8, 9, true));
        lineList.add(new Line(5, 8, true));
        lineList.add(new Line(6, 9, true));
        lineList.add(new Line(9, 10, true));

        // 先将List<Line>变成Map<fromNode,List<toNode>>
        Map<Integer, List<Integer>> lineMap = lineList.stream()
                .collect(Collectors.groupingBy(Line::fromNodeId,
                        Collectors.mapping(Line::toNodeId, Collectors.toList())));
        CutFlowLine cutFlowLine = new CutFlowLine();
        List<Line> newLine = cutFlowLine.setNewLine(nodeList, lineMap);
        // 查看结果
        Map<Integer, List<Node>> nodeCollect = nodeList.stream()
                .collect(Collectors.groupingBy(Node::nodeId));
        for (Line line : newLine) {
            System.out.printf("form: %s to: %s\n",
                    nodeCollect.get(line.fromNodeId()).get(0).name(),
                    nodeCollect.get(line.toNodeId()).get(0).name());
        }
    }

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
                if (!hasRel) {
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
        }
        return newLineList;
    }
}
