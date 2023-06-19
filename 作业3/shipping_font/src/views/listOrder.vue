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
        <div style="width: 100%;padding: 0px" >
          <!--          寄出信息表格-->
          <div style="height: 40vh;border-bottom: 3px dotted #eee">
            <div style="display: flex;height: 40px;margin-bottom: 10px">
              <span class="r-g-index r-g-index-1" style="margin-left: 20px">寄</span>
              <span class="" style="margin-left: 10px;line-height: 30px;font-weight: bolder">我寄出的</span>
            </div>
            <div style="">
              <el-table
                  :data="send_tableData.filter(data => !search || data.recname.toLowerCase().includes(search.toLowerCase()))"
                  style="width: 80%;margin-left: 3vw"
                  height="30vh"
                  :row-class-name="tableRowClassName">
                <el-table-column
                    prop="recname"
                    label="收件人"
                    width="100">
                </el-table-column>
                <el-table-column
                    prop="recphone"
                    label="联系方式"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="destination"
                    label="收货地址">
                </el-table-column>
                <el-table-column
                    prop="type"
                    label="运输方式">
                </el-table-column>
                <el-table-column
                    prop="esttime"
                    label="预计时间">
                </el-table-column>
                <el-table-column
                    prop="state"
                    label="当前状态"
                    width="100">
                  <template slot-scope="scope">
                    <el-tag
                        v-if="scope.row.state === '未发货'"
                        :type="'info'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                    <el-tag
                        v-if="scope.row.state === '运输中'"
                        :type="'primary'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                    <el-tag
                        v-if="scope.row.state === '待签收'"
                        :type="'warning'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                    <el-tag
                        v-if="scope.row.state === '已完成'"
                        :type="'success'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot="header" slot-scope="scope">
                    <el-input
                        v-model="search"
                        size="mini"
                        placeholder="输入关键字搜索"/>
                  </template>
                  <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">详情</el-button>
                    <el-button
                        v-if="scope.row.state === '待签收'"
                        size="mini"
                        type="success"
                        @click="handleDelete(scope.$index, scope.row)">签收</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <!--          收货信息表格-->
          <div style="margin-top: 2vh">
            <div style="display: flex;height: 40px;margin-bottom: 10px">
              <span class="r-g-index r-g-index-2" style="margin-left: 20px">收</span>
              <span class="" style="margin-left: 10px;line-height: 30px;font-weight: bolder">我收到的</span>
            </div>
            <div style="">
              <el-table
                  :data="rec_tableData.filter(data => !search_2 || data.sendname.toLowerCase().includes(search_2.toLowerCase()))"
                  style="width: 80%;margin-left: 3vw"
                  height="30vh"
                  :row-class-name="tableRowClassName">
                <el-table-column
                    prop="sendname"
                    label="寄件人"
                    width="100">
                </el-table-column>
                <el-table-column
                    prop="sendphone"
                    label="联系方式"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="setout"
                    label="寄件地址">
                </el-table-column>
                <el-table-column
                    prop="type"
                    label="运输方式">
                </el-table-column>
                <el-table-column
                    prop="esttime"
                    label="预计时间">
                </el-table-column>
                <el-table-column
                    prop="state"
                    label="当前状态"
                    width="100">
                  <template slot-scope="scope">
                    <el-tag
                        v-if="scope.row.state === '未发货'"
                        :type="'info'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                    <el-tag
                        v-if="scope.row.state === '运输中'"
                        :type="'primary'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                    <el-tag
                        v-if="scope.row.state === '待签收'"
                        :type="'warning'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                    <el-tag
                        v-if="scope.row.state === '已完成'"
                        :type="'success'"
                        disable-transitions>{{scope.row.state}}</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot="header" slot-scope="scope">
                    <el-input
                        v-model="search_2"
                        size="mini"
                        placeholder="输入关键字搜索"/>
                  </template>
                  <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">详情</el-button>
                    <el-button
                        v-if="scope.row.state === '待签收'"
                        size="mini"
                        type="success"
                        @click="handleStatus(scope.$index, scope.row)">签收</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>

        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from "axios";
import my_aside from "@/components/my_aside";
import my_header from "@/components/my_header";
import {regionData} from "element-china-area-data";

export default {
  name: "listOrder",
  components:{
    my_aside,
    my_header
  },
  data(){
    return{
      state_options: [{
        value: '未发货',
        label: '未发货'
      }, {
        value: '运输中',
        label: '运输中'
      }, {
        value: '待签收',
        label: '待签收'
      }, {
        value: '已完成',
        label: '已完成'
      }, ],
      tempid:'',
      tempState: '',
      changeState:false,
      user:{},
      search: '',
      search_2: '',
      send_tableData: [
        {
          recname:'ZLG',
          recphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'未发货',
        },
        {
          recname:'ZLG',
          recphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'运输中',
        },
        {
          recname:'ZLG',
          recphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'待签收',
        },
        {
          recname:'ZLG',
          recphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'已完成',
        },
        {
          recname:'ZLG',
          recphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'待签收',
        }
      ],
      all_tableData:[],
      rec_tableData: [
        {
          sendname:'禹浩男喵',
          sendphone: "18224426057",
          setout:"北京市海淀区",
          type:'陆运',
          state:'未发货',
        },
        {
          sendname:'ZLG',
          sendphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'运输中',
        },
        {
          sendname:'ZLG',
          sendphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'待签收',
        },
        {
          sendname:'ZLG',
          sendphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'已完成',
        },
        {
          sendname:'ZLG',
          sendphone: "18224426057",
          destination:"北京市海淀区",
          type:'陆运',
          state:'待签收',
        }
      ],
      check:true,
      role:0,
      options: regionData,
      curpage:1,
      place_1:'',
      place_2:'',
      ruleForm: {
      },
      rules: {
        sendname: [
          { required: true, message: '请输入寄件人姓名', trigger: 'blur' },
        ],
        sendphone: [
          { required: true, message: '请选择寄件人联系方式', trigger: 'blur' }
        ],
        createtime: [
          { required: true, message: '请选择发货日期', trigger: 'blur' }
        ],
        setout: [
          {required: true, message: '请选择发货地', trigger: 'blur' }
        ],
        destination: [
          {required: true, message: '请选择收货地', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请填写发货地址', trigger: 'blur' }
        ],
        recaddress: [
          { required: true, message: '请填写收货地址', trigger: 'blur' }
        ],
        weight: [
          { required: true, message: '请填写货物重量', trigger: 'blur' }
        ],
        recname: [
          { required: true, message: '请填写收件人姓名', trigger: 'blur' }
        ],
        recphone: [
          { required: true, message: '请填写收件人手机', trigger: 'blur' }
        ],
      },

    }
  },
  created() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    this.token= JSON.parse(window.localStorage.getItem("token"));
    this.getSend();
    this.getRec();
    // this.getAll();
  },
  methods:{
    getSend(){
      var _this=this;
      this.send_tableData=[]
      console.log(this.user)
      axios
          .get("http://localhost:8088/order/selectBysp?&phone="+ this.user.phone, {
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
          })
          .then(function (ressponse) {
            _this.send_tableData=ressponse.data
          })
      // console.log(this.send_tableData)
    },
    getRec(){
      var _this=this;
      this.rec_tableData=[]
      axios
          .get("http://localhost:8088/order/selectByrp?&phone="+ this.user.phone, {
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
          })
          .then(function (ressponse) {
            _this.rec_tableData=ressponse.data;
          })
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    getAll(){
      var _this=this;
      this.all_tableData=[]
      console.log(this.user)
      axios
          .get("http://localhost:8088/order/selectAll", {
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
          })
          .then(function (ressponse) {
            _this.all_tableData=ressponse.data
          })
      // console.log(this.send_tableData)
    },
  },

}
</script>

<style scoped>

.bock01 {
  border: 1px solid rgba(0,0,0,0.10);
  width: 50%;
  box-shadow: 0 0 10px 0 rgba(0,0,0,0.05);
  overflow: hidden;
  padding-top: 35px;
  padding-bottom: 27px;
  margin-left: 5px;
  margin-right: 5px;
}

.r-g-index {
  width: 30px;
  height: 30px;
  background: #cccccc;
  color: #fff;
  text-align: center;
  line-height: 30px;
  font-weight: bolder;
  border-radius: 10px;
}
.r-g-index.r-g-index-1 {
  background: black;
}
.r-g-index.r-g-index-2 {
  background: darkred;
}

.el-header{
  vertical-align: center;
  border-bottom: 3px solid #eee;
  margin: 5px;
  border-radius: 5px;
}
.el-menu{
  border-radius: 0px  25px 0px 0px;
  border-right: 0px;
  font-size: 30px;
}
.el-menu-item{
  text-align: start;
  font-size: 18px;
  font-weight: bold;
  height: 60px;
}

.el-main{
  /*border: 3px solid #eee;*/
  margin: 5px;
  border-radius: 5px;
  box-shadow: 0px 0px 2px black;
}
.el-menu-item{
}

body {
  display: block;
  margin: 0px;
}
</style>