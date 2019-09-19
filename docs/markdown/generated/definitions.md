
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



