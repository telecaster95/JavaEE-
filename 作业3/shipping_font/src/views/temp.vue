<template>
  <el-container>
    <el-aside width="200px">
      <my_aside />
    </el-aside>
    <el-container>
      <!--      头部-->
      <el-header>
        <my_header />
      </el-header>
      <!--      主要内容-->
      <el-main>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import my_aside from "@/components/my_aside";
import my_header from "@/components/my_header";

export default {
  name: "temp",
  components:{
    my_aside,
    my_header
  },
  data(){
    return {
    }
  },
  created() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    this.token= JSON.parse(window.localStorage.getItem("token"));
  },
  methods:{
  },
}
</script>

<style scoped>

</style>