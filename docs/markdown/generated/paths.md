
<a name="paths"></a>
## 资源

<a name="labelcontroller_resource"></a>
### LabelController
标签操作API接口


<a name="addlabelusingput"></a>
#### 给媒体添加标签
```
PUT /ctc/media/label/add
```


##### 说明
根据路径获取媒体,添加标签


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

* `application/json`


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
#### 删除媒体标签
```
DELETE /ctc/media/label/delete
```


##### 说明
根据路径获取媒体，删除标签


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

* `application/json`


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
|**200**|OK|string|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `application/json`


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
"string"
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

* `application/json`


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
#### 获取媒体
```
GET /ctc/media/get
```


##### 说明
根据路径获取媒体对象


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|媒体路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[媒体实体类](#cf54fa6458ec523f408b87237e6378e1)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `application/json`


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
|**Query**|**pageSize**  <br>*可选*|每页数量|integer (int32)|


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

* `application/json`


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
#### 媒体批量上传
```
POST /ctc/media/upload
```


##### 说明
批量上传


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**file**  <br>*必填*|file|< file > array(multi)|
|**Body**|**media**  <br>*可选*|媒体实体对象|[媒体实体类](#cf54fa6458ec523f408b87237e6378e1)|


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

* `application/json`


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


<a name="directory-controller_resource"></a>
### Directory-controller
Directory Controller


<a name="listdirectoriesusingget"></a>
#### listDirectories
```
GET /ctc/directory/list
```


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
/ctc/directory/list
```


##### HTTP响应示例

###### 响应 200
```
json :
[ "string" ]
```


<a name="file-mgm-controller_resource"></a>
### File-mgm-controller
File Mgm Controller


<a name="deletemediausingdelete"></a>
#### deleteMedia
```
DELETE /ctc/media/delete
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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


###### 请求 query
```
json :
{
  "path" : "string"
}
```


<a name="getownerusingget"></a>
#### getOwner
```
GET /ctc/media/owner/get
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


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
#### updateOwner
```
POST /ctc/media/owner/update
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**owner**  <br>*必填*|owner|string|
|**Query**|**path**  <br>*必填*|path|string|


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
#### renameMedia
```
POST /ctc/media/rename
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**afterName**  <br>*必填*|afterName|string|
|**Query**|**originalPath**  <br>*必填*|originalPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
  "originalPath" : "string"
}
```


<a name="updatetypeusingpost"></a>
#### updateType
```
POST /ctc/media/type/update
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|
|**Query**|**type**  <br>*必填*|type|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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


<a name="show-controller_resource"></a>
### Show-controller
Show Controller


<a name="showthumbnailusingpost"></a>
#### showThumbnail
```
POST /ctc/media/show/thumbnail
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**thumbnailPath**  <br>*必填*|thumbnailPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/thumbnail
```


###### 请求 query
```
json :
{
  "thumbnailPath" : "string"
}
```


<a name="showthumbnailusingget"></a>
#### showThumbnail
```
GET /ctc/media/show/thumbnail
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**thumbnailPath**  <br>*必填*|thumbnailPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/thumbnail
```


###### 请求 query
```
json :
{
  "thumbnailPath" : "string"
}
```


<a name="showthumbnailusingput"></a>
#### showThumbnail
```
PUT /ctc/media/show/thumbnail
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**thumbnailPath**  <br>*必填*|thumbnailPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/thumbnail
```


###### 请求 query
```
json :
{
  "thumbnailPath" : "string"
}
```


<a name="showthumbnailusingdelete"></a>
#### showThumbnail
```
DELETE /ctc/media/show/thumbnail
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**thumbnailPath**  <br>*必填*|thumbnailPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/thumbnail
```


###### 请求 query
```
json :
{
  "thumbnailPath" : "string"
}
```


<a name="showthumbnailusingpatch"></a>
#### showThumbnail
```
PATCH /ctc/media/show/thumbnail
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**thumbnailPath**  <br>*必填*|thumbnailPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/thumbnail
```


###### 请求 query
```
json :
{
  "thumbnailPath" : "string"
}
```


<a name="showthumbnailusinghead"></a>
#### showThumbnail
```
HEAD /ctc/media/show/thumbnail
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**thumbnailPath**  <br>*必填*|thumbnailPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/thumbnail
```


###### 请求 query
```
json :
{
  "thumbnailPath" : "string"
}
```


<a name="showthumbnailusingoptions"></a>
#### showThumbnail
```
OPTIONS /ctc/media/show/thumbnail
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**thumbnailPath**  <br>*必填*|thumbnailPath|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/thumbnail
```


###### 请求 query
```
json :
{
  "thumbnailPath" : "string"
}
```


<a name="showvideousingpost"></a>
#### showVideo
```
POST /ctc/media/show/video
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


<a name="showvideousingget"></a>
#### showVideo
```
GET /ctc/media/show/video
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


<a name="showvideousingput"></a>
#### showVideo
```
PUT /ctc/media/show/video
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


<a name="showvideousingdelete"></a>
#### showVideo
```
DELETE /ctc/media/show/video
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


<a name="showvideousingpatch"></a>
#### showVideo
```
PATCH /ctc/media/show/video
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


<a name="showvideousinghead"></a>
#### showVideo
```
HEAD /ctc/media/show/video
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```


<a name="showvideousingoptions"></a>
#### showVideo
```
OPTIONS /ctc/media/show/video
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**path**  <br>*必填*|path|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|无内容|
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
/ctc/media/show/video
```


###### 请求 query
```
json :
{
  "path" : "string"
}
```



