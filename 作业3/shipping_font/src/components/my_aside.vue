<template>
    <el-menu
            router
            :default-active="this.$route.path"
            style="height: 100vh"
            default-active="1"
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
        <!--        <h3 style="color: white;font-size: 30px;margin-top: 3vh;margin-bottom: 3vh">Title</h3>-->
        <div style="width: 200px; height: 100px;margin-top: 2vh">
            <img class="img" src="~@/assets/shiptitle_white.png" draggable="false"
                 style="display: block; width: 200px; height: 100px;">
        </div>
        <el-menu-item index="/center" @click="curpage=0" style="margin-top: 3vh">
            <i class="el-icon-menu"></i>
            <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/newOrder">
            <i class="el-icon-s-promotion"></i>
            <span slot="title">新增订单</span>
        </el-menu-item>
        <!--    <el-menu-item index="/manageOrder" v-if="user.identity==1" @click="curpage_2()">-->
        <!--      <i class="el-icon-s-check"></i>-->
        <!--      <span slot="title">订单管理</span>-->
        <!--    </el-menu-item>-->
        <el-submenu index="/manageOrder">
            <template slot="title">
                <i class="el-icon-location"></i>
                <span>订单管理</span>
            </template>
            <el-menu-item-group>
                <el-menu-item index="/manageOrder"><i class="el-icon-location"></i>全部订单</el-menu-item>
                <el-menu-item index="1-2"><i class="el-icon-location"></i>待发货</el-menu-item>
            </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="3" v-if="user.identity==1" @click="curpage=3">
            <i class="el-icon-menu"></i>
            <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="/listOrder">
            <i class="el-icon-s-check"></i>
            <span slot="title">订单查询</span>
        </el-menu-item>
    </el-menu>
</template>

<script>

export default {
    data() {
        return {}
    },
    created() {
        this.user = JSON.parse(window.localStorage.getItem("user"));
        this.token = JSON.parse(window.localStorage.getItem("token"));
    }
}
</script>