(function (e) {
    function t(t) {
        for (var o, l, a = t[0], i = t[1], u = t[2], f = 0, p = []; f < a.length; f++) l = a[f], Object.prototype.hasOwnProperty.call(r, l) && r[l] && p.push(r[l][0]), r[l] = 0;
        for (o in i) Object.prototype.hasOwnProperty.call(i, o) && (e[o] = i[o]);
        s && s(t);
        while (p.length) p.shift()();
        return c.push.apply(c, u || []), n()
    }

    function n() {
        for (var e, t = 0; t < c.length; t++) {
            for (var n = c[t], o = !0, a = 1; a < n.length; a++) {
                var i = n[a];
                0 !== r[i] && (o = !1)
            }
            o && (c.splice(t--, 1), e = l(l.s = n[0]))
        }
        return e
    }

    var o = {}, r = {app: 0}, c = [];

    function l(t) {
        if (o[t]) return o[t].exports;
        var n = o[t] = {i: t, l: !1, exports: {}};
        return e[t].call(n.exports, n, n.exports, l), n.l = !0, n.exports
    }

    l.m = e, l.c = o, l.d = function (e, t, n) {
        l.o(e, t) || Object.defineProperty(e, t, {enumerable: !0, get: n})
    }, l.r = function (e) {
        "undefined" !== typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {value: "Module"}), Object.defineProperty(e, "__esModule", {value: !0})
    }, l.t = function (e, t) {
        if (1 & t && (e = l(e)), 8 & t) return e;
        if (4 & t && "object" === typeof e && e && e.__esModule) return e;
        var n = Object.create(null);
        if (l.r(n), Object.defineProperty(n, "default", {
            enumerable: !0,
            value: e
        }), 2 & t && "string" != typeof e) for (var o in e) l.d(n, o, function (t) {
            return e[t]
        }.bind(null, o));
        return n
    }, l.n = function (e) {
        var t = e && e.__esModule ? function () {
            return e["default"]
        } : function () {
            return e
        };
        return l.d(t, "a", t), t
    }, l.o = function (e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
    }, l.p = "/";
    var a = window["webpackJsonp"] = window["webpackJsonp"] || [], i = a.push.bind(a);
    a.push = t, a = a.slice();
    for (var u = 0; u < a.length; u++) t(a[u]);
    var s = i;
    c.push([0, "chunk-vendors"]), n()
})({
    0: function (e, t, n) {
        e.exports = n("56d7")
    }, "034f": function (e, t, n) {
        "use strict";
        var o = n("85ec"), r = n.n(o);
        r.a
    }, 4586: function (e, t, n) {
        "use strict";
        var o = n("7977"), r = n.n(o);
        r.a
    }, "56d7": function (e, t, n) {
        "use strict";
        n.r(t);
        n("e260"), n("e6cf"), n("cca6"), n("a79d");
        var o = n("2b0e"), r = function () {
                var e = this, t = e.$createElement, n = e._self._c || t;
                return n("div", {attrs: {id: "app"}}, [n("HelloWorld", {attrs: {msg: "Welcome to Your Vue.js App"}})], 1)
            }, c = [], l = function () {
                var e = this, t = e.$createElement, n = e._self._c || t;
                return n("div", {staticClass: "hello"}, [e._v(" " + e._s(e.msg) + " "), n("form", {
                    on: {
                        submit: function (e) {
                            e.preventDefault()
                        }
                    }
                }, [n("input", {
                    ref: "imgs",
                    attrs: {type: "file"}
                }), n("button", {on: {click: e.upload}}, [e._v("上传文件")])]), n("img", {
                    staticClass: "img",
                    attrs: {src: e.imgUrl}
                })])
            }, a = [], i = (n("99af"), n("b0c0"), n("d3b7"), n("cea2")), u = n("bc3a"), s = n.n(u),
            f = "http://pic.stadc.cn/", p = {
                name: "HelloWorld", props: {msg: String}, data: function () {
                    return {imgUrl: ""}
                }, methods: {
                    upload: function () {
                        var e = this, t = this, n = new Promise((function (e) {
                            s.a.get("http://localhost:8080/uptoken").then((function (t) {
                                var n = t.data.token;
                                e(n)
                            }))
                        }));
                        n.then((function (n) {
                            var o = e.$refs.imgs.files[0];
                            console.log("file:", o);
                            var r = o.name, c = {useCdnDomain: !1}, l = {fname: r, mimeType: null},
                                a = i["upload"](o, r, n, l, c);
                            console.log("observer", a);
                            a.subscribe({
                                next: function (e) {
                                    console.log("next:", e), 100 === e.total.percent && console.log("done")
                                }, error: function (e) {
                                    console.log("error:"), console.dir(e)
                                }, complete: function (n) {
                                    var o = n.key;
                                    t.imgUrl = "".concat(f).concat(o), e.imgUrl = "".concat(f).concat(o), console.log("this", e), console.log("that(vue)", t), console.log("that(vue)===this", t === e)
                                }
                            })
                        }))
                    }
                }
            }, d = p, m = (n("4586"), n("2877")), v = Object(m["a"])(d, l, a, !1, null, "efa8e028", null), g = v.exports,
            h = {name: "App", components: {HelloWorld: g}}, b = h,
            y = (n("034f"), Object(m["a"])(b, r, c, !1, null, null, null)), O = y.exports;
        o["a"].config.productionTip = !1, new o["a"]({
            render: function (e) {
                return e(O)
            }
        }).$mount("#app")
    }, 7977: function (e, t, n) {
    }, "85ec": function (e, t, n) {
    }
});
//# sourceMappingURL=app.00ff2bdc.js.map