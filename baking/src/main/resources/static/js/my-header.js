Vue.component('my-header', {
    data: function () {
        return {
            user: localStorage.user ? JSON.parse(localStorage.user) : null,
            currentIndex: "",
            wd:""
        }
    },
    methods: {
        search(){
            if (this.wd.trim()==""){
                this.$message.error("Search");
                return;
            }
            location.href="/contentList.html?wd="+this.wd;
        },
        logout() {
            if (confirm("Log out?")) {
                axios.get("/v1/users/logout").then(function () {
                    localStorage.clear();
                    location.href = "/";
                })
            }

        },
        handleSelect(key, keyPath) {
            if (key == 0) {
                location.href = "/";
            } else {
                location.href = "/contentList.html?type=" + key;
            }
        }
    },
    created: function () {
        if (location.search.includes("type")) {
            let type = new URLSearchParams(location.search).get("type");
            if (location.pathname!="/postArticle.html"){
                this.currentIndex = type;
            }
        } else {//index & other
            if (location.pathname == "/" || location.pathname == "/index.html") {
                this.currentIndex = "0";
            }
        }
    },
    template: `
        <el-header height="80px">
            <div class="center">
                <el-row gutter="20">
                    <el-col span="6">
                        <a href="/">
                            <img src="imgs/icon.png" width="196" height="65">
                        </a>
                        
                    </el-col>
                    <el-col span="10">
                        <el-menu :default-active="currentIndex" @select="handleSelect" mode="horizontal" active-text-color="orange">
                            <el-menu-item index="0">Home</el-menu-item>
                            <el-menu-item index="1">Recipe</el-menu-item>
                            <el-menu-item index="2">Video</el-menu-item>
                            <el-menu-item index="3">Information</el-menu-item>
                        </el-menu>
                    </el-col>
                    <el-col span="6">
                        <el-input style="position: relative;top: 15px" @keydown.native.enter="search()" v-model="wd" placeholder="Search">
                            <el-button @click="search()" slot="append" icon="el-icon-search"></el-button>
                        </el-input>
                    </el-col>
                    <el-col span="2">
                        <el-popover v-if="user==null"
                                placement="top-start"
                                title="Welcome to Baking World!"
                                width="200"
                                trigger="hover">
                            <div slot="reference">
                                <i style="font-size: 30px;position: relative;top: 20px"
                                   class="el-icon-user"></i>
                            </div>
                            <el-button type="info" @click="location.href='/reg.html'">Create Account</el-button>
                            <el-button type="warning" @click="location.href='/login.html'">Log In</el-button>
                        </el-popover>
                        <el-popover v-else
                                placement="top-start"
                                title="Welcome to Baking World!"
                                width="200"
                                trigger="hover">
                            <div slot="reference">
                                <i style="font-size: 30px;position: relative;top: 20px"
                                   class="el-icon-user"></i>
                            </div>
                            <div style="text-align: center">
                                <a href="/personal.html">
                                    <!--<el-avatar :src="user.imgUrl"></el-avatar>-->
                                    <img :src="user.imgUrl" 
                                    style="width: 50px;height: 50px;border-radius: 50px">
                                </a>
                                <hr>
                                <a href="/personal.html">My Account</a>
                                <a href="javascript:void(0)" @click="logout()">Log Out</a><br>
                                <a v-if="user.isAdmin==1" href="/admin.html">Backend management page</a>
                            </div>
                        </el-popover>
                    </el-col>
                </el-row>
            </div>
        </el-header>
    `
})