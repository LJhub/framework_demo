var app = new Vue({
    el:"#app",
    data:{
        userList:[],
        user:{}
    },
    methods:{
        findAll: function () {
            axios.get("../user/findAll").then(function (response) {
                app.userList=response.data;
            }).catch(function (error) {
                alert(error);
            });
        },
        findOne: function (id) {
            axios.get("../user/findOne?id="+id).then(function (response) {
                app.user=response.data;
            }).catch(function (error) {
                alert(error)
            })
        },
        update:function () {
            axios.post("../user/update",app.user).then(function (response) {
                app.findAll();
            }).catch(function (error) {
                alert(error);
            })
        }
    },
    created: function () {
        this.findAll();
    }
});