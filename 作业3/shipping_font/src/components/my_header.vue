<template>
  <!--      头部-->
  <el-header>
    <el-popover
        placement="bottom"
        title="个人信息"
        width="300"
        trigger="click"
        content="将来放关于用户的信息。"
        style="float: right;align-content: center;margin-top: 1vh"
    >
      <el-button v-if="check" slot="reference"  icon="el-icon-user-solid" circle
                 style="background-color: #ffd04b">
      </el-button>

      <el-button v-if="!check" slot="reference"  icon="el-icon-user" circle
                 style="background-color: #ffd04b">
      </el-button>
      <!--用户信息 -->
      <div v-if="check">
        欢迎回来！<span style="color: #55ab41">{{
          user.username
        }}</span>
      </div>
      <div v-if="user.identity==0">
        用户身份：<span style="color: orange;font-weight: bolder">
              普通用户
            </span>
      </div>
      <div v-if="user.identity==1">
        用户身份：<span style="color: red;font-weight: bolder">
              后台管理员
            </span>
      </div>
      <div >
            <span @click="logout()" style="color: green;font-weight: bolder">
              注销
            </span>
      </div>

      <!--          <div v-if="!check">-->
      <!--            <a-->
      <!--                style="-->
      <!--                    color: #55ab41;-->
      <!--                    margin-right: 148px;-->
      <!--                    text-decoration: none;-->
      <!--                  "-->
      <!--                href="/login"-->
      <!--            >对不起,请前往登录</a-->
      <!--            >-->
      <!--            <a-->
      <!--                style="color: #55ab41; text-decoration: none"-->
      <!--                href="/register"-->
      <!--            >注册</a-->
      <!--            >-->
      <!--          </div>-->
    </el-popover>
    <!--        <div style="background-color: gray;height: 7vh"></div>-->
  </el-header>


</template>

<script>
export default {
  name: "my_header",
  data(){
    return {
      check:true,
    }
  },
  created() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    this.token= JSON.parse(window.localStorage.getItem("token"));
  },
  methods:{
    logout(){
      var _this = this;
      window.localStorage.removeItem("user");
      _this.$message({
        message: "注销成功",
        type: "success",
      });
      setTimeout(() => {
        location.reload();
      }, 1000);

    },

  },
}
</script>

<style scoped>

</style>