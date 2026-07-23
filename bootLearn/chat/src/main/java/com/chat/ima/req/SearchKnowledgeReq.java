package com.chat.ima.req;

/**
 * 搜索知识库内容请求
 *
 * @param query             搜索关键词
 * @param cursor            游标，首次传空串
 * @param knowledge_base_id 知识库 ID
 */
public record SearchKnowledgeReq(String query, String cursor, String knowledge_base_id) {

    public static SearchKnowledgeReq build(String kbId, String query) {
        return new SearchKnowledgeReq(query, "", kbId);
    }
}
