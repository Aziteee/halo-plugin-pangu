# Halo Plugin Pangu

自动对文章内容执行 [Pangu](https://github.com/vinta/pangu.js) 格式化，在中英文之间加入空格。

## 配置

### 格式化方案

#### 1. 浏览器端格式化（默认）

在浏览器中注入 pangu.js 对网页文本格式化。

该方案能够处理所有页面的任意内容，但由于需要 dom 加载完成后才能执行，可能会有文字位移的情况发生。

#### 2. 服务器端格式化

在后端对文章格式化后再发送给浏览器。

该方案避免了文字位移的问题，但是可能会有处理错误的情况发生，并且只能处理文章和自定义页面的内容。

#### 3. 同时启用

同时使用两种格式化方式

### 文章内容选择器

> 该配置仅限浏览器端格式化可用

填写需要格式化的区域的选择器，如 `#content` ，不填则默认为 `body`（即整个页面）。

## TODO

- [ ] 服务器端格式化支持处理文章概览和标题
