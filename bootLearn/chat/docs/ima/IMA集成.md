# IMA集成

> 2026-07-22 20:58:35

## 获取ApiKey

1. 确保 **IMA 桌面客户端已安装且正在运行**
2. 打开浏览器访问 **https://ima.qq.com/agent-interface**
3. 点击「**获取 API Key**」→ 会跳转到本地 IMA 客户端生成凭据
4. 如果之前申请过，先点「**删除**」→ 再「**重新获取**」
5. 拿到新的 `Client ID` 和 `API Key` 后，更新配置文件

## 获取kb_id

* 目前只有调用api接口获取
* 请求示例

```http
### 查询知识库列表
POST https://ima.qq.com/openapi/wiki/v1/search_knowledge_base
Content-Type: application/json
ima-openapi-clientid: clientid
ima-openapi-apikey: apikey

{
  "query": "",
  "cursor": "",
  "limit": 20
}
```

* 响应示例（取其中的`kb_id`字段）

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "info_list": [
      {
        "kb_id": "this-is-kb-id",
        "kb_name": "xx的知识库",
        "cover_url": "",
        "member_count": "1",
        "content_count": "2",
        "description": "",
        "creator": "xx",
        "role_type": "创建者",
        "base_type": "个人知识库"
      }
    ],
    "is_end": true,
    "next_cursor": "xxx"
  },
  "request_id": "xxx"
}
```

