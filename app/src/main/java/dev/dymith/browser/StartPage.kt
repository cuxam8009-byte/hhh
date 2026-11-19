package dev.dymith.browser

/** Trang chủ Dymith tối giản: đồng hồ, truy cập nhanh và dock tìm kiếm cố định. */
object StartPage {
    const val URL = "https://start.dymith.invalid/"

    val HTML: String = """
<!doctype html>
<html lang="vi">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover, interactive-widget=resizes-content">
<meta name="theme-color" content="#FFFFFF">
<title>Dymith</title>
<style>
:root{
  --bg:#FFFFFF;--ink:#000000;--mute:#737373;--surf:#F5F5F5;--line:#E5E5E5;
  --acc:#000000;--acc-ink:#FFFFFF;--sh:0 8px 24px rgba(0,0,0,.08);
  --native-bottom-inset:0px;
  color-scheme:light;box-sizing:border-box;
}
@media(prefers-color-scheme:dark){
  :root:not([data-theme="light"]){
    --bg:#000000;--ink:#FFFFFF;--mute:#A3A3A3;--surf:#121212;--line:#2A2A2A;
    --acc:#FFFFFF;--acc-ink:#000000;--sh:none;color-scheme:dark;
  }
}
:root[data-theme="dark"]{
  --bg:#000000;--ink:#FFFFFF;--mute:#A3A3A3;--surf:#121212;--line:#2A2A2A;
  --acc:#FFFFFF;--acc-ink:#000000;--sh:none;color-scheme:dark;
}
*,*::before,*::after{box-sizing:inherit}
html,body{width:100%;height:100%;margin:0}
html{background:var(--bg);scroll-padding-top:env(safe-area-inset-top,0px)}
body{
  overflow:hidden;background:var(--bg);color:var(--ink);
  font:16px/1.4 ui-rounded,"SF Pro Rounded",system-ui,-apple-system,"Segoe UI",Roboto,sans-serif;
  -webkit-tap-highlight-color:transparent;
}
button,input{font:inherit;color:inherit}
button{cursor:pointer;border:0;background:none;padding:0;-webkit-appearance:none;appearance:none}
:focus-visible{outline:2px solid var(--acc);outline-offset:2px}

.stage{
  width:100%;height:100%;max-width:520px;margin:0 auto;display:flex;flex-direction:column;
  padding:24px 0 96px;overflow-y:auto;overscroll-behavior:contain;scrollbar-width:none;
}
.stage::-webkit-scrollbar{display:none}
.top{padding:0 24px;margin-bottom:auto}
.hello{margin:0;color:var(--mute);font-size:15px}
.clock{margin:2px 0 0;font-size:clamp(68px,24vw,104px);font-weight:300;letter-spacing:-.045em;line-height:1;font-variant-numeric:tabular-nums}
.date{margin:8px 0 0;color:var(--mute);font-size:15px;text-transform:capitalize}

.recent{display:flex;gap:8px;padding:28px 20px 16px;overflow-x:auto;scrollbar-width:none;overscroll-behavior-x:contain}
.recent::-webkit-scrollbar,.chips::-webkit-scrollbar{display:none}
.recent:empty{display:none}
.recent button{flex:none;display:flex;align-items:center;gap:8px;max-width:70vw;padding:9px 14px;border:1px solid var(--line);border-radius:999px;font-size:14px;background:var(--surf)}
.recent span{overflow:hidden;text-overflow:ellipsis;white-space:nowrap}

.arc{display:grid;grid-template-columns:repeat(4,minmax(0,1fr));gap:22px 4px;padding:8px 12px 6px;margin-top:0}
.recent:empty+.arc{margin-top:28px}
.arc button{--dy:0px;display:flex;flex-direction:column;align-items:center;gap:7px;min-width:0;transform:translateY(var(--dy));}
.arc button:nth-child(4n+1),.arc button:nth-child(4n){--dy:-18px}
.ic{position:relative;width:58px;height:58px;border-radius:19px;display:grid;place-items:center;overflow:hidden;font-weight:700;font-size:19px;background:var(--surf);color:var(--ink)}
.ic .fallback{font-size:18px;font-weight:700}
.ic img{position:absolute;inset:0;width:100%;height:100%;object-fit:contain;border-radius:19px;padding:12px;background:var(--surf);opacity:0}
.arc small{font-size:12px;color:var(--mute);max-width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}
.arc button:active .ic{transform:scale(.94)}

.scrim{position:fixed;inset:0;background:var(--bg);opacity:0;pointer-events:none;z-index:5}
body.open .scrim{opacity:.86;pointer-events:auto}
.dock{
  position:fixed;left:0;right:0;bottom:var(--native-bottom-inset,0px);z-index:10;
  padding:0 12px 12px;pointer-events:none;
}
.dock>*{max-width:520px;margin-inline:auto;pointer-events:auto}
.panel{
  display:flex;flex-direction:column;margin-bottom:8px;background:var(--surf);border:1px solid var(--line);
  border-radius:24px;box-shadow:var(--sh);max-height:min(60vh,440px);overflow-y:auto;overscroll-behavior:contain;
}
.panel[hidden]{display:none}
.sug{list-style:none;margin:0;padding:6px;display:flex;flex-direction:column}
.sug:empty{display:none}
.sug li:first-child .row{font-weight:650}
.row{display:flex;align-items:center;gap:12px;width:100%;min-height:48px;padding:0 12px;border-radius:16px;text-align:left}
.row:active{background:var(--bg)}
.glyph{display:block;flex:none;width:20px;height:20px;color:var(--mute);pointer-events:none}
.row span{flex:1;min-width:0;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}
.row small{color:var(--mute);font-size:13px}
.chips{display:flex;gap:8px;padding:10px;overflow-x:auto;scrollbar-width:none;overscroll-behavior-x:contain}
.sug:not(:empty)+.chips{border-top:1px solid var(--line)}
.chip{flex:none;padding:8px 14px;border:1px solid var(--line);border-radius:999px;font-size:14px;background:var(--bg)}
.chip[aria-checked="true"]{background:var(--acc);border-color:var(--acc);color:var(--acc-ink);font-weight:600}
.chip.ghost{color:var(--mute);border-style:dashed}

.bar{display:flex;align-items:center;gap:8px;height:58px;padding:6px;background:var(--surf);border:1px solid var(--line);border-radius:29px;box-shadow:var(--sh)}
.eng{flex:none;width:44px;height:44px;border-radius:50%;background:var(--bg);font-weight:700;color:var(--ink)}
#q{flex:1;min-width:0;height:44px;border:0;background:none;font-size:16px;outline:0;color:var(--ink)}
#q::placeholder{color:var(--mute)}
.bar:focus-within{border-color:var(--acc)}
.go{flex:none;width:44px;height:44px;border-radius:50%;display:grid;place-items:center;background:var(--bg);color:var(--mute)}
.go.on{background:var(--acc);color:var(--acc-ink)}
.go .glyph{color:currentColor}
@media(max-width:360px){
  .top{padding-inline:20px}.stage{padding-top:20px;padding-bottom:90px}
  .clock{font-size:clamp(62px,23vw,88px)}.ic{width:52px;height:52px;border-radius:17px}
  .arc{gap:18px 4px}.recent{padding-left:16px;padding-right:16px}
}
@media(max-height:680px){
  .stage{padding-top:14px;padding-bottom:88px}.clock{font-size:clamp(58px,21vw,84px)}
  .recent{padding-top:18px;padding-bottom:10px}.arc{gap:15px 4px}
}
@media(prefers-reduced-motion:reduce){*{scroll-behavior:auto!important;transition:none!important;animation:none!important}}
</style>
</head>
<body>
<main class="stage">
  <header class="top">
    <p class="hello" id="hello"></p>
    <h1 class="clock" id="clock">00:00</h1>
    <p class="date" id="date"></p>
  </header>
  <section class="recent" id="recent" aria-label="Tìm gần đây"></section>
  <section class="arc" id="arc" aria-label="Lối tắt website"></section>
</main>
<div class="scrim" id="scrim" aria-hidden="true"></div>
<div class="dock" id="dock">
  <div class="panel" id="panel" hidden>
    <ul class="sug" id="sug" aria-label="Gợi ý tìm kiếm"></ul>
    <div class="chips" id="chips" role="radiogroup" aria-label="Công cụ tìm kiếm"></div>
  </div>
  <div class="bar">
    <button class="eng" id="engBtn" type="button" aria-label="Đổi công cụ tìm kiếm">G</button>
    <input id="q" type="text" placeholder="Tìm kiếm hoặc nhập địa chỉ" enterkeyhint="go" autocomplete="off" autocapitalize="off" autocorrect="off" spellcheck="false" aria-label="Tìm kiếm hoặc nhập địa chỉ">
    <button class="go" id="goBtn" type="button" aria-label="Đi"></button>
  </div>
</div>
<script>
(function(){
'use strict';
const $=id=>document.getElementById(id);
const SITES=[
  ["YouTube","youtube.com","Y"],
  ["Facebook","facebook.com","f"],
  ["Zalo","chat.zalo.me","Z"],
  ["Shopee","shopee.vn","S"],
  ["VnExpress","vnexpress.net","V"],
  ["Gmail","mail.google.com","G"],
  ["GitHub","github.com","GH"],
  ["Wikipedia","vi.wikipedia.org","W"]
];
const ENG=[
  ["Google","https://www.google.com/search?q=%s"],
  ["DuckDuckGo","https://duckduckgo.com/?q=%s"],
  ["Bing","https://www.bing.com/search?q=%s"],
  ["Cốc Cốc","https://coccoc.com/search?query=%s"]
];
const THEME_LABEL={auto:"Tự động",light:"Sáng",dark:"Tối"};
const STORE={
  get(key,fallback){try{const raw=localStorage.getItem("dymith.home."+key);return raw===null?fallback:JSON.parse(raw)}catch(_){return fallback}},
  set(key,value){try{localStorage.setItem("dymith.home."+key,JSON.stringify(value))}catch(_){}}
};
let engine=STORE.get("engine",0);
if(!Number.isInteger(engine)||engine<0||engine>=ENG.length)engine=0;
let history=STORE.get("history",[]);
if(!Array.isArray(history))history=[];
history=history.filter(x=>typeof x==="string").slice(0,8);
let theme=STORE.get("theme","auto");
if(!Object.prototype.hasOwnProperty.call(THEME_LABEL,theme))theme="auto";
const q=$("q"),panel=$("panel"),suggestions=$("sug"),chips=$("chips"),recent=$("recent"),goButton=$("goBtn"),engineButton=$("engBtn");
const glyphPaths={
  go:c=>{c.moveTo(5,12);c.lineTo(19,12);c.moveTo(13,6);c.lineTo(19,12);c.lineTo(13,18)},
  search:c=>{c.arc(10.5,10.5,6.7,0,Math.PI*2);c.moveTo(15.5,15.5);c.lineTo(20,20)},
  clock:c=>{c.arc(12,12,9,0,Math.PI*2);c.moveTo(12,7);c.lineTo(12,12);c.lineTo(16,14)},
  globe:c=>{c.arc(12,12,9,0,Math.PI*2);c.moveTo(3,12);c.lineTo(21,12);c.moveTo(12,3);c.bezierCurveTo(7,7,7,17,12,21);c.bezierCurveTo(17,17,17,7,12,3)}
};
function drawGlyph(canvas){
  const size=20,dpr=Math.min(window.devicePixelRatio||1,2);
  if(canvas.dataset.drawn==="1"&&canvas.width===Math.round(size*dpr)&&canvas.height===Math.round(size*dpr))return;
  canvas.width=Math.round(size*dpr);canvas.height=Math.round(size*dpr);
  const ctx=canvas.getContext("2d",{alpha:true});if(!ctx)return;
  ctx.setTransform(dpr,0,0,dpr,0,0);
  ctx.strokeStyle=getComputedStyle(canvas).color;ctx.lineWidth=2;ctx.lineCap="round";ctx.lineJoin="round";
  const name=canvas.dataset.icon,draw=glyphPaths[name];if(!draw)return;
  ctx.beginPath();draw(ctx);ctx.stroke();canvas.dataset.drawn="1";
}
function glyph(name){
  const canvas=document.createElement("canvas");canvas.className="glyph";canvas.width=40;canvas.height=40;
  canvas.dataset.icon=name;canvas.setAttribute("aria-hidden","true");
  return canvas;
}
function redrawGlyphs(root=document,force=false){root.querySelectorAll("canvas[data-icon]").forEach(canvas=>{if(force)canvas.dataset.drawn="0";drawGlyph(canvas)})}
function persistHistory(){STORE.set("history",history)}
function isUrl(value){
  const s=String(value||"").trim();
  if(!s||/\s/.test(s))return false;
  if(/^https?:\/\/\S+$/i.test(s))return true;
  return /^(?:localhost|(?:\d{1,3}\.){3}\d{1,3}|(?:[a-z0-9-]+\.)+[a-z]{2,})(?::\d{1,5})?(?:\/[^\s]*)?$/i.test(s);
}
function fallbackUrl(input,template){
  if(isUrl(input))return /^https?:\/\//i.test(input)?input:((/^(?:localhost|(?:\d{1,3}\.){3}\d{1,3})(?::|\/|$)/i.test(input)?"http://":"https://")+input);
  return template.replace("%s",encodeURIComponent(input));
}
function nativeMessage(payload){
  try{
    if(window.Dymith&&typeof window.Dymith.postMessage==="function"){
      window.Dymith.postMessage(JSON.stringify(payload));return true;
    }
  }catch(_){}
  return false;
}
function openUrl(url){
  if(!/^https?:\/\//i.test(url))return;
  if(!nativeMessage({action:"open",url:url}))window.location.href=url;
}
function go(raw){
  const value=String(raw||"").trim();if(!value)return;
  history=[value].concat(history.filter(item=>item.toLowerCase()!==value.toLowerCase())).slice(0,8);
  persistHistory();renderRecent();
  const template=ENG[engine][1];
  const sent=nativeMessage({action:"navigate",input:value,template:template});
  if(!sent)window.location.href=fallbackUrl(value,template);
  q.value="";q.blur();setOpen(false);syncBar();
}
function makeRow(kind,label,query,sub){
  const li=document.createElement("li"),button=document.createElement("button"),text=document.createElement("span");
  button.type="button";button.className="row";button.appendChild(glyph(kind));
  text.textContent=label;button.appendChild(text);
  if(sub){const small=document.createElement("small");small.textContent=sub;button.appendChild(small)}
  if(query!==null)button.dataset.q=query;
  li.appendChild(button);return li;
}
function renderSuggestions(){
  const value=q.value.trim(),lower=value.toLowerCase(),fragment=document.createDocumentFragment();
  if(value){
    fragment.appendChild(makeRow(isUrl(value)?"globe":"search",isUrl(value)?"Mở "+value:"Tìm “"+value+"” trên "+ENG[engine][0],value));
    SITES.filter(site=>site[0].toLowerCase().includes(lower)||site[1].toLowerCase().includes(lower)).slice(0,3)
      .forEach(site=>{const item=makeRow("globe",site[0],null,site[1]);item.firstElementChild.dataset.url="https://"+site[1];fragment.appendChild(item)});
    history.filter(item=>item.toLowerCase().includes(lower)&&item.toLowerCase()!==lower).slice(0,3)
      .forEach(item=>fragment.appendChild(makeRow("clock",item,item)));
  }else{
    history.slice(0,5).forEach(item=>fragment.appendChild(makeRow("clock",item,item)));
  }
  suggestions.replaceChildren(fragment);redrawGlyphs(suggestions);
}
function renderRecent(){
  const fragment=document.createDocumentFragment();
  history.slice(0,4).forEach(item=>{
    const button=document.createElement("button"),text=document.createElement("span");
    button.type="button";button.dataset.q=item;button.appendChild(glyph("clock"));
    text.textContent=item;button.appendChild(text);fragment.appendChild(button);
  });
  recent.replaceChildren(fragment);redrawGlyphs(recent);
}
function renderShortcuts(){
  const fragment=document.createDocumentFragment();
  SITES.forEach(site=>{
    const button=document.createElement("button"),icon=document.createElement("span"),fallback=document.createElement("span"),label=document.createElement("small"),image=document.createElement("img");
    button.type="button";button.dataset.url="https://"+site[1];button.style.setProperty("--i",String(fragment.childNodes.length));
    icon.className="ic";fallback.className="fallback";fallback.textContent=site[2];icon.appendChild(fallback);
    image.alt="";image.loading="lazy";image.decoding="async";image.referrerPolicy="no-referrer";
    image.src="https://www.google.com/s2/favicons?domain="+encodeURIComponent(site[1])+"&sz=128";
    image.addEventListener("load",()=>{fallback.hidden=true;image.style.opacity="1"},{once:true});
    image.addEventListener("error",()=>{image.remove()},{once:true});
    icon.appendChild(image);label.textContent=site[0];
    button.appendChild(icon);button.appendChild(label);fragment.appendChild(button);
  });
  $("arc").replaceChildren(fragment);
}
function renderChips(){
  const fragment=document.createDocumentFragment();
  ENG.forEach((item,index)=>{
    const button=document.createElement("button");button.type="button";button.className="chip";
    button.setAttribute("role","radio");button.dataset.eng=String(index);button.textContent=item[0];
    button.setAttribute("aria-checked",String(index===engine));fragment.appendChild(button);
  });
  const themeButton=document.createElement("button");themeButton.type="button";themeButton.className="chip ghost";
  themeButton.dataset.act="theme";fragment.appendChild(themeButton);
  const clearButton=document.createElement("button");clearButton.type="button";clearButton.className="chip ghost";
  clearButton.dataset.act="clear";clearButton.textContent="Xóa lịch sử";clearButton.hidden=history.length===0;fragment.appendChild(clearButton);
  chips.replaceChildren(fragment);syncChips();
}
function syncChips(){
  chips.querySelectorAll("[data-eng]").forEach(button=>button.setAttribute("aria-checked",String(Number(button.dataset.eng)===engine)));
  const themeButton=chips.querySelector('[data-act="theme"]');if(themeButton)themeButton.textContent="Giao diện: "+THEME_LABEL[theme];
  const clearButton=chips.querySelector('[data-act="clear"]');if(clearButton)clearButton.hidden=history.length===0;
}
function syncBar(){
  engineButton.textContent=ENG[engine][0].charAt(0);
  goButton.classList.toggle("on",!!q.value.trim());
}
function setOpen(open){
  document.body.classList.toggle("open",open);panel.hidden=!open;
  if(open)renderSuggestions();
}
function applyTheme(){
  const root=document.documentElement;
  if(theme==="auto")root.removeAttribute("data-theme");else root.setAttribute("data-theme",theme);
  redrawGlyphs(document,true);STORE.set("theme",theme);syncChips();
  nativeMessage({action:"theme",theme:theme});
}
function pick(event){
  const button=event.target.closest("button");if(!button)return;
  const data=button.dataset;
  if(data.url){openUrl(data.url);return}
  if(data.q!==undefined){go(data.q);return}
  if(data.eng!==undefined){engine=Number(data.eng);STORE.set("engine",engine);renderSuggestions();syncBar();syncChips();return}
  if(data.act==="theme"){
    theme=theme==="auto"?"light":theme==="light"?"dark":"auto";applyTheme();return;
  }
  if(data.act==="clear"){
    history=[];persistHistory();renderRecent();renderSuggestions();syncChips();return;
  }
}
function tickClock(){
  const now=new Date(),hour=now.getHours(),time=String(hour).padStart(2,"0")+":"+String(now.getMinutes()).padStart(2,"0");
  $("clock").textContent=time;
  $("hello").textContent=hour<11?"Chào buổi sáng":hour<14?"Chào buổi trưa":hour<18?"Chào buổi chiều":"Chào buổi tối";
  $("date").textContent=now.toLocaleDateString("vi-VN",{weekday:"long",day:"numeric",month:"long"});
  clockTimer=setTimeout(tickClock,60000-now.getSeconds()*1000-now.getMilliseconds()+40);
}
let clockTimer=0;
function startClock(){clearTimeout(clockTimer);tickClock()}
function stopClock(){clearTimeout(clockTimer);clockTimer=0}

goButton.appendChild(glyph("go"));
renderShortcuts();renderRecent();renderChips();syncBar();applyTheme();startClock();
q.addEventListener("focus",()=>setOpen(true));
q.addEventListener("blur",()=>setOpen(false));
q.addEventListener("input",()=>{renderSuggestions();syncBar()});
q.addEventListener("keydown",event=>{
  if(event.key==="Enter"){event.preventDefault();go(q.value)}
  else if(event.key==="Escape"){q.blur()}
});
$("scrim").addEventListener("click",()=>q.blur());
goButton.addEventListener("click",()=>q.value.trim()?go(q.value):q.focus());
engineButton.addEventListener("click",()=>{
  engine=(engine+1)%ENG.length;STORE.set("engine",engine);syncBar();syncChips();
  if(document.activeElement!==q)q.focus();else renderSuggestions();
});
panel.addEventListener("click",pick);$("arc").addEventListener("click",pick);recent.addEventListener("click",pick);
chips.addEventListener("click",pick);
$("dock").addEventListener("pointerdown",event=>{
  if(event.target.closest("button"))event.preventDefault();
},{passive:false});
document.addEventListener("visibilitychange",()=>document.hidden?stopClock():startClock());
window.addEventListener("resize",()=>redrawGlyphs(document,true),{passive:true});
window.addEventListener("pageshow",startClock,{passive:true});
window.addEventListener("pagehide",stopClock,{passive:true});
})();
</script>
</body>
</html>
""".trimIndent()
}
