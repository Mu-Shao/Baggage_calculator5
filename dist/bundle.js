(()=>{function e(e,t){let l=document.getElementById(e).childNodes;for(let e=0,n=l.length;e<n;e++)1===l[e].nodeType&&(l[e].disabled=!t)}window.onload=function(){document.getElementById("flightRegion").disabled=!0,document.getElementById("flightAreaOptionDefault").value="0",document.getElementById("vipType").disabled=!1,e("inland",!0),e("outland",!1)},document.getElementById("addBtn").addEventListener("click",(function(e){let t=document.getElementById("baggageSum").value,l="<tr>"+$("#baggageRow").html()+"</tr>";l=l.replace(/00/g,++t),l=l.replace(/value="0"/g,'value=""'),$("#baggageTable tr:last").after(l),$("#baggageSum").val(t)}))})();