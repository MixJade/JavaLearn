# Hutool发送http请求

> 2024年7月9日10:45:18

## 一、post请求登录

```java
/**
 * 通过post请求进行登录
 *
 * @return 登录成功
 */
private boolean login() {
    JSONObject jb = new JSONObject();
    jb.set("username", myUserName);
    jb.set("password", myPassword);
    log.info("login body={}", jb);
    // 在`execute()`前的body是post的请求体传参，之后的body是返回值的响应体
    String responseBody = HttpRequest.post("http://127.0.0.1:9999/login")
            .header("Content-Type", "application/json")
            .body(jb.toString())
            .execute()
            .body();
    log.info("login body={}", responseBody);
    // 最后结果为字符串,需要通过Json工具来转化
    LoginRes logRes = JsonUtil.readValue(responseBody, LoginRes.class);
    if (logRes.getStatus() == 200) {
        String accessToken = logRes.getAccessToken();
        log.info("Token为{}", accessToken);
        token = accessToken; // 将Token存入全局变量
        return true;
    } else {
        log.info("登录失败");
        return false;
    }
}
```

## 二、上传多个文件

```java
/**
 * 上传多个文件
 *
 * @return 上传成功
 */
private boolean uploadFiles() {
    String fileNm1 = "sss.pdf";
    String fileNm2 = "sss2.pdf";
    // 先从本地服务器下载文件并转成二进制数据
    ResponseEntity<byte[]> fileByte1 = fileService.downloadFile(fileNm1);
    ResponseEntity<byte[]> fileByte2 = fileService.downloadFile(fileNm2);
    // 再用hutool的FileUtil转为文件
    File file1 = FileUtil.writeBytes(fileByte1.getBody(), fileNm1);
    File file2 = FileUtil.writeBytes(fileByte2.getBody(), fileNm2);

    // 发送请求
    log.info("开始上传文件");
    String responseBody = HttpRequest.post("http://127.0.0.1:9999/create/task")
            // 请求头改用上传文件的形式
            .header("Content-Type", "multipart/form-data")
            // 从全局变量中取出登录时的Token,并拼接请求头的Token
            .header("Authorization", "Bearer " + token)
            .form("files", file1, file2)
            .execute()
            .body();
    log.info("创建比对任务 body={}", responseBody);
    UploadResult resp = JsonUtil.readValue(responseBody, UploadResult.class);
    if (resp != null && resp.getCode() != null) {
        // 上传成功
        return true
    } else {
        return false;
    }
    return false;
}
```

## 三、GET请求查状态

```java
/**
 * 通过GET请求查询任务状态
 *
 * @param taskId 任务ID
 * @return 状态码
 */
private int status(String taskId) {
    String url = "http://127.0.0.1:9999/task/status?taskId=" + taskId;
    log.info("status url={}", url);
    String responseBody = HttpRequest.get(url)
            .header("Content-Type", "application/json")
            // 从全局变量中取出登录时的Token,并拼接请求头的Token
            .header("Authorization", "Bearer " + token)
            .execute()
            .body();
    log.info("status body={}", responseBody);
    QueryStatusRes resp = JsonUtil.readValue(responseBody, QueryStatusRes.class);
    if (resp == null) return 0;
    if (resp.getStatus() == 200) {
        return resp.getData().getCode();
    } else {
        log.info("查询状态失败");
        return 0;
    }
}
```

