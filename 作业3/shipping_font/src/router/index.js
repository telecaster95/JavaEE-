import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)


const Login = () => import('../views/Login')
const Center= () =>import('../views/center')
const newOrder = () => import('../views/newOrder')
const manageOrder= () =>import('../views/manageOrder')
const listOrder = () => import('../views/listOrder')


const routes = [
    { path: '/', component: Login,meta:{requireAuth:false}},
    { path: '/login', component: Login,meta:{requireAuth:false} },
    { path: '/center', component: Center,meta:{requireAuth:true} },
    { path: '/newOrder', component: newOrder,meta:{requireAuth:true} },
    { path: '/manageOrder', component: manageOrder,meta:{requireAuth:true} },
    { path: '/listOrder', component: listOrder,meta:{requireAuth:true} },
    { path: '/*', redirect: '/' }  // 当匹配不上时重定向的到首页
]



const router = new Router({
    routes,
    mode: "history"
})

router.beforeEach((to, from, next) => {
    var _this = this;
    if(to.meta.requireAuth ){
        if(JSON.parse(window.localStorage.getItem("user"))==null){
            console.log('没有登录')
            router.push({path: '/login',query: {}})
            next()
        } else {
            console.log('登了')
            // router.push({path: to.fullPath})
            next()
        }
    }
    else {
        next();
    }
})

export default router
