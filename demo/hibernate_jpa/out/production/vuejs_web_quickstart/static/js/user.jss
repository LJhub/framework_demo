var vue = new Vue({
    el:"#app",
    data:{
        userList:[], // 封装列表中的数据
        user:{}  // 封装查询的用户信息
    },
    methods:{
        // 查询所有
        findAll:function() {
            var _this = this;
            axios.get("./user/findAll").then(function (response) {
                // 在axios中的this，已经不再表示vue对象，表示Window对象
                // 解决方案：使用vue对象去调用，或者使用全局变量
                // alert(this.alert("ok"));
                // alert(JSON.stringify(response.data));
                _this.userList = response.data;
            }).catch(function (error) {

            })
        },
        // 主键查询
        // findOne:function (id) {
        //     var _this = this;
        //     axios.get("./user/findOne",{params:{id:id}}).then(function (response) {
        //         _this.user = response.data;
        //     }).catch(function (error) {
        //
        //     })
        // }
        // 使用restful
        findOne:function (id) {
            var _this = this;
            axios.get("./user/findOne/"+id).then(function (response) {
                _this.user = response.data;
            }).catch(function (error) {

            })
        },
        // 修改
        update:function () {
            var _this = this;
            axios.post("./user/update",this.user).then(function (response) {
                _this.findAll();
            }).catch(function (error) {
                alert("保存失败...");
            })
        }
    },
    created:function () {
        this.findAll();
    }
	
})