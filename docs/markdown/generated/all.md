# CTC REST API Interface 设计文档


<a name="overview"></a>
## 概览
CTC REST API Interface


### URI scheme
*域名* : localhost:8082  
*基础路径* : /


### 标签

* FileMgmController : 文件管理操作API接口
* LabelController : 标签操作API接口
* MediaController : 媒体操作API接口




<a name="paths"></a>
## 资源

<a name="filemgmcontroller_resource"></a>
### FileMgmController
文件管理操作API接口


<a name="deletemediausingdelete"></a>
#### 删除视频
```
DELETE /ctc/media/delete
```


##### 说明
根据路径删除视频


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/delete
```


###### 请求 body
```
json :
{ }
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="listdirectoriesusingget"></a>
#### 列出目录下的所有子目录
```
GET /ctc/media/directory/list
```


##### 说明
列出文件保存目录下的所有子目录


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string > array|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/directory/list
```


##### HTTP响应示例

###### 响应 200
```
json :
[ "string" ]
```


<a name="getownerusingget"></a>
#### 获取文件owner
```
GET /ctc/media/owner/get
```


##### 说明
根据路径获取文件owner


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/owner/get
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="updateownerusingpost"></a>
#### 更新文件owner
```
POST /ctc/media/owner/update
```


##### 说明
根据路径获取文件owner


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**owner**  <br>*可选*|新的拥有者|string|
|**Query**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/owner/update
```


###### 请求 query
```
json :
{
  "owner" : "string",
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="renamemediausingpost"></a>
#### 更新文件名
```
POST /ctc/media/rename
```


##### 说明
根据路径更新文件名


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**afterName**  <br>*必填*|更新后的文件名|string|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/rename
```


###### 请求 query
```
json :
{
  "afterName" : "string",
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showthumbnailusingpost"></a>
#### 文件路径
```
POST /ctc/media/thumbnail
```


##### 说明
根据路径获取缩略图路径


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/thumbnail
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showthumbnailusingget"></a>
#### 文件路径
```
GET /ctc/media/thumbnail
```


##### 说明
根据路径获取缩略图路径


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/thumbnail
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showthumbnailusingput"></a>
#### 文件路径
```
PUT /ctc/media/thumbnail
```


##### 说明
根据路径获取缩略图路径


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/thumbnail
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showthumbnailusingdelete"></a>
#### 文件路径
```
DELETE /ctc/media/thumbnail
```


##### 说明
根据路径获取缩略图路径


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/thumbnail
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showthumbnailusingpatch"></a>
#### 文件路径
```
PATCH /ctc/media/thumbnail
```


##### 说明
根据路径获取缩略图路径


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/thumbnail
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showthumbnailusinghead"></a>
#### 文件路径
```
HEAD /ctc/media/thumbnail
```


##### 说明
根据路径获取缩略图路径


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/thumbnail
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showthumbnailusingoptions"></a>
#### 文件路径
```
OPTIONS /ctc/media/thumbnail
```


##### 说明
根据路径获取缩略图路径


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/thumbnail
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="updatetypeusingpost"></a>
#### 更新保密级别
```
POST /ctc/media/type/update
```


##### 说明
根据路径更新保密级别


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|媒体路径|string|
|**Query**|**type**  <br>*可选*|新的保密级别|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/type/update
```


###### 请求 query
```
json :
{
  "path" : "string",
  "type" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showvideousingpost"></a>
#### 文件路径
```
POST /ctc/media/video
```


##### 说明
根据路径获取视频地址


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showvideousingget"></a>
#### 文件路径
```
GET /ctc/media/video
```


##### 说明
根据路径获取视频地址


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showvideousingput"></a>
#### 文件路径
```
PUT /ctc/media/video
```


##### 说明
根据路径获取视频地址


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showvideousingdelete"></a>
#### 文件路径
```
DELETE /ctc/media/video
```


##### 说明
根据路径获取视频地址


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showvideousingpatch"></a>
#### 文件路径
```
PATCH /ctc/media/video
```


##### 说明
根据路径获取视频地址


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showvideousinghead"></a>
#### 文件路径
```
HEAD /ctc/media/video
```


##### 说明
根据路径获取视频地址


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="showvideousingoptions"></a>
#### 文件路径
```
OPTIONS /ctc/media/video
```


##### 说明
根据路径获取视频地址


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="labelcontroller_resource"></a>
### LabelController
标签操作API接口


<a name="addlabelusingput"></a>
#### 给文件添加标签
```
PUT /ctc/media/label/add
```


##### 说明
根据路径获取文件,添加标签


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**labelName**  <br>*必填*|添加的标签名|string|
|**Query**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/label/add
```


###### 请求 query
```
json :
{
  "labelName" : "string",
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="deletelabelusingdelete"></a>
#### 删除文件标签
```
DELETE /ctc/media/label/delete
```


##### 说明
根据路径获取文件，删除标签


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**labelName**  <br>*必填*|删除的标签名|string|
|**Query**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/label/delete
```


###### 请求 query
```
json :
{
  "labelName" : "string",
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="getlabelusingget"></a>
#### 获取媒体标签
```
GET /ctc/media/label/get
```


##### 说明
根据路径获取标签


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**401**|Unauthorized|无内容|
|**403**|禁止访问|无内容|
|**404**|无内容|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/label/get
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="mediacontroller_resource"></a>
### MediaController
媒体操作API接口


<a name="downloadusingget"></a>
#### 断点下载
```
GET /ctc/media/download
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/download
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="getmediabypathusingget"></a>
#### 获取文件
```
GET /ctc/media/get
```


##### 说明
根据路径获取文件对象


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«媒体实体类»](#4cc072d06eb3ef869efa586081cfba52)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/get
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : {
    "category" : "string",
    "createTime" : "string",
    "downloadNum" : 0,
    "ext" : "string",
    "fileSize" : 0,
    "labels" : "string",
    "mediaName" : "string",
    "owner" : "string",
    "path" : "string",
    "thumbnailPath" : "string",
    "type" : "string",
    "viewNum" : 0
  },
  "msg" : "string"
}
```


<a name="paginationusingget"></a>
#### 分页
```
GET /ctc/media/pagination
```


##### 说明
根据参数分页


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**pageNum**  <br>*可选*|页数|integer (int32)|
|**Query**|**pageSize**  <br>*可选*|单页数量|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[PageInfo«媒体实体类»](#a5bb089cae440b83f4038b7cc131240d)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/pagination
```


###### 请求 query
```
json :
{
  "pageNum" : 0,
  "pageSize" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "endRow" : 0,
  "firstPage" : 0,
  "hasNextPage" : true,
  "hasPreviousPage" : true,
  "isFirstPage" : true,
  "isLastPage" : true,
  "lastPage" : 0,
  "list" : [ {
    "category" : "string",
    "createTime" : "string",
    "downloadNum" : 0,
    "ext" : "string",
    "fileSize" : 0,
    "labels" : "string",
    "mediaName" : "string",
    "owner" : "string",
    "path" : "string",
    "thumbnailPath" : "string",
    "type" : "string",
    "viewNum" : 0
  } ],
  "navigateFirstPage" : 0,
  "navigateLastPage" : 0,
  "navigatePages" : 0,
  "navigatepageNums" : [ 0 ],
  "nextPage" : 0,
  "pageNum" : 0,
  "pageSize" : 0,
  "pages" : 0,
  "prePage" : 0,
  "size" : 0,
  "startRow" : 0,
  "total" : 0
}
```


<a name="uploadusingpost"></a>
#### 文件批量上传
```
POST /ctc/media/upload
```


##### 说明
批量上传


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**file**  <br>*必填*|file|< file > array(multi)|
|**Body**|**media**  <br>*可选*|文件实体对象|[媒体实体类](#cf54fa6458ec523f408b87237e6378e1)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResultModel«string»](#5f0004be64abbe156f532ae433fab4d6)|
|**201**|Created|无内容|
|**401**|未授权|无内容|
|**403**|无权限|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/ctc/media/upload
```


###### 请求 query
```
json :
{
  "file" : "file"
}
```


###### 请求 body
```
json :
{
  "category" : "string",
  "createTime" : "string",
  "downloadNum" : 0,
  "ext" : "string",
  "fileSize" : 0,
  "labels" : "string",
  "mediaName" : "string",
  "owner" : "string",
  "path" : "string",
  "thumbnailPath" : "string",
  "type" : "string",
  "viewNum" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```




<a name="definitions"></a>
## 定义

<a name="a5bb089cae440b83f4038b7cc131240d"></a>
### PageInfo«媒体实体类»

|名称|说明|类型|
|---|---|---|
|**endRow**  <br>*可选*|**样例** : `0`|integer (int32)|
|**firstPage**  <br>*可选*|**样例** : `0`|integer (int32)|
|**hasNextPage**  <br>*可选*|**样例** : `true`|boolean|
|**hasPreviousPage**  <br>*可选*|**样例** : `true`|boolean|
|**isFirstPage**  <br>*可选*|**样例** : `true`|boolean|
|**isLastPage**  <br>*可选*|**样例** : `true`|boolean|
|**lastPage**  <br>*可选*|**样例** : `0`|integer (int32)|
|**list**  <br>*可选*|**样例** : `[ "[cf54fa6458ec523f408b87237e6378e1](#cf54fa6458ec523f408b87237e6378e1)" ]`|< [媒体实体类](#cf54fa6458ec523f408b87237e6378e1) > array|
|**navigateFirstPage**  <br>*可选*|**样例** : `0`|integer (int32)|
|**navigateLastPage**  <br>*可选*|**样例** : `0`|integer (int32)|
|**navigatePages**  <br>*可选*|**样例** : `0`|integer (int32)|
|**navigatepageNums**  <br>*可选*|**样例** : `[ 0 ]`|< integer (int32) > array|
|**nextPage**  <br>*可选*|**样例** : `0`|integer (int32)|
|**pageNum**  <br>*可选*|**样例** : `0`|integer (int32)|
|**pageSize**  <br>*可选*|**样例** : `0`|integer (int32)|
|**pages**  <br>*可选*|**样例** : `0`|integer (int32)|
|**prePage**  <br>*可选*|**样例** : `0`|integer (int32)|
|**size**  <br>*可选*|**样例** : `0`|integer (int32)|
|**startRow**  <br>*可选*|**样例** : `0`|integer (int32)|
|**total**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="5f0004be64abbe156f532ae433fab4d6"></a>
### ResultModel«string»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `"string"`|string|
|**data**  <br>*可选*|**样例** : `"string"`|string|
|**msg**  <br>*可选*|**样例** : `"string"`|string|


<a name="4cc072d06eb3ef869efa586081cfba52"></a>
### ResultModel«媒体实体类»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `"string"`|string|
|**data**  <br>*可选*|**样例** : `"[cf54fa6458ec523f408b87237e6378e1](#cf54fa6458ec523f408b87237e6378e1)"`|[媒体实体类](#cf54fa6458ec523f408b87237e6378e1)|
|**msg**  <br>*可选*|**样例** : `"string"`|string|


<a name="cf54fa6458ec523f408b87237e6378e1"></a>
### 媒体实体类
媒体实体对象


|名称|说明|类型|
|---|---|---|
|**category**  <br>*必填*|文件夹  <br>**样例** : `"string"`|string|
|**createTime**  <br>*必填*|创建时间  <br>**样例** : `"string"`|string|
|**downloadNum**  <br>*可选*|下载量  <br>**样例** : `0`|integer (int32)|
|**ext**  <br>*必填*|扩展名  <br>**样例** : `"string"`|string|
|**fileSize**  <br>*必填*|文件大小  <br>**样例** : `0`|integer (int64)|
|**labels**  <br>*可选*|标签  <br>**样例** : `"string"`|string|
|**mediaName**  <br>*必填*|媒体名  <br>**样例** : `"string"`|string|
|**owner**  <br>*必填*|拥有者  <br>**样例** : `"string"`|string|
|**path**  <br>*必填*|媒体路径  <br>**样例** : `"string"`|string|
|**thumbnailPath**  <br>*可选*|缩略图路径  <br>**样例** : `"string"`|string|
|**type**  <br>*必填*|保密级别  <br>**样例** : `"string"`|string|
|**viewNum**  <br>*可选*|点击量  <br>**样例** : `0`|integer (int32)|





