/**

 @Name: 案例

 */

layui.define(['laypage', 'fly'], function(exports){

  var $ = layui.jquery;
  var layer = layui.layer;
  var util = layui.util;
  var laytpl = layui.laytpl;
  var form = layui.form;
  var laypage = layui.laypage;
  var upload = layui.upload;
  var fly = layui.fly;
  var device = layui.device();


  //求解管理
  var active = {
    //提交案例
    push: function(div){
      layer.open({
        type: 1
        ,id: 'LAY_pushcase'
        ,title: '发布图片'
        ,area: (device.ios || device.android) ? ($(window).width() + 'px') : '660px'
        ,content: ['<ul class="layui-form" style="margin: 20px;">'
          ,'<li class="layui-form-item">'
            ,'<label class="layui-form-label">图片地址</label>'
            ,'<div class="layui-input-block">'
              ,'<input required name="link" lay-verify="url" name="pic" id="pic" placeholder="可复制图片链接地址" value="" class="layui-input">'
            ,'</div>'
          ,'</li>'
          ,'<li class="layui-form-item">'
            ,'<label class="layui-form-label">上传图片</label>'
            ,'<div class="layui-input-inline" style="width:auto;">'
              ,'<input type="hidden" name="cover" class="layui-input fly-case-image">'
              ,'<button type="button" class="layui-btn layui-btn-primary" id="caseUpload">'
                ,'<i class="layui-icon">&#xe67c;</i>上传图片'
              ,'</button>'
            ,'</div>'
            ,'<div class="layui-form-mid layui-word-aux" id="preview">推荐尺寸：478*300，大小不能超过 30kb</div>'
          ,'</li>'
          ,'<li class="layui-form-item">'
            ,'<label class="layui-form-label"> </label>'
            ,'<div class="layui-input-block">'
              ,'<input type="checkbox" name="agree" id="agree" title="我同意（如果你进行了刷赞行为，你的案例将被立马剔除）" lay-skin="primary">'
            ,'</div>'
          ,'</li>'
          ,'<li class="layui-form-item">'
            ,'<div class="layui-input-block">'
              ,'<button type="button" lay-submit lay-filter="pushCase" class="layui-btn">提交案例</button>'
           ,'</div>'
          ,'</li>'
        ,'</ul>'].join('')
        ,success: function(layero, index){
          var image = layero.find('.fly-case-image')
          ,pic = layero.find("#pic"),preview = $('#preview');

          upload.render({
            url: '/api/upload/'
            ,elem: '#caseUpload'
            ,size: 200
            ,done: function(res){
              if(res.status == 0){
                image.val(res.url);
                pic.val(res.url);
                preview.html('<a href="'+ res.url +'" target="_blank" style="color: #5FB878;">图片已上传，点击可预览</a>');
              } else {
                layer.msg(res.msg, {icon: 5});
              }
            }
          });

          form.render('checkbox').on('submit(pushCase)', function(data){
            if(!data.field.agree){
              return layer.tips('你需要同意才能提交', $('#agree').next(), {tips: 1});
            }

              fly.json('/beauty/push', {pic: data.field.link}, function (res) {
                  layer.alert(res.msg, {
                      icon: 1,
                  })
              });
            layer.closeAll();
          });
          }
      });
    }

    //点赞
    ,praise: function(othis){
      var li = othis.parents('li')
      ,PRIMARY = 'layui-btn-primary'
      ,unpraise = !othis.hasClass(PRIMARY)
      ,numElem = li.find('.fly-case-nums')

      fly.json('/beauty/praise', {
        id: li.data('id')
        ,unpraise: unpraise ? true : null
      }, function(res){
        if(res.status == 0) {
            numElem.html(res.praise);
            if (unpraise) {
                othis.addClass(PRIMARY).html('点赞');
                layer.tips('少了个赞 :(', numElem, {
                    tips: 1
                });
            } else {
                othis.removeClass(PRIMARY).html('已赞');
                layer.tips('多了个赞 :)', numElem, {
                    tips: [1, '#FF5722']
                });
            }
        }else{
          layer.msg(res.msg);
        }
      });
    }

    // 批准
      ,approve: function(othis){
          var li = othis.parents('li')
              ,PRIMARY = 'layui-btn-primary'
              ,unpraise = !othis.hasClass(PRIMARY)
              ,numElem = li.find('.fly-case-nums')

          fly.json('/beauty/approve', {
              id: li.data('id')
          }, function(res){
             layer.msg(res.msg, {time: 1000},function () {
                 if(res.status == 0){
                     parent.location.reload();
                 }
             });
          });
      }
      // 删除
      ,delpic: function(othis){
          var li = othis.parents('li')
              ,PRIMARY = 'layui-btn-primary'
              ,unpraise = !othis.hasClass(PRIMARY)
              ,numElem = li.find('.fly-case-nums')

          fly.json('/beauty/delete', {
              id: li.data('id')
          }, function(res){
              layer.msg(res.msg, {time: 1000}, function () {
                  if(res.status == 0){
                      parent.location.reload();
                  }
              });
          });
      }

    //查看点赞用户
    ,showPraise: function(othis){
      var li = othis.parents('li');
      if(othis.html() == 0) return layer.tips('该项目还没有收到赞', othis, {
        tips: 1
      });
      fly.json('/case/praise_user/', {
        id: li.data('id')
      }, function(res){
        var html = '';
        layer.open({
          type: 1
          ,title: '项目【'+ res.title + '】获得的赞'
          ,id: 'LAY_showPraise'
          ,shade: 0.8
          ,shadeClose: true
          ,area: '305px'
          ,skin: 'layer-ext-case'
          ,content: function(){
            layui.each(res.data, function(_, item){
              html += '<li><a href="/u/'+ 168*item.id +'/" target="_blank" title="'+ item.username +'"><img src="'+ item.avatar +'"></a></li>'
            });
            return '<ul class="layer-ext-ul">' + html + '</ul>';
          }()
        })
      });
    }
  };

  $('body').on('click', '.fly-case-active', function(){
    var othis = $(this), type = othis.data('type');
    active[type] && active[type].call(this, othis);
  });

  exports('case', {});
});