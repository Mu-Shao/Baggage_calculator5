function createBaggage(index, data) {
    return {
        'type': data['baggageType' + index],
        'length': parseFloat(data['length' + index]),
        'width': parseFloat(data['width' + index]),
        'height': parseFloat(data['height' + index]),
        'weight': parseFloat(data['weight' + index])
    };
}

function calculate() {
    var data = {};
    data.flightType = $("#flightType").val(); //返回输入框的值
    data.flightArea = $("#flightRegion").val();
    data.seatType = $("#cabinType").val();
    data.peopleType = $("#passengerType").val();
    data.vipType = $("#vipType").val();
    data.ticketPrice = $("#price").val();// 获取表单中的数据
    // data.isDisability = $("#isDisability").val();
    data.isDisability = document.getElementById('isDisability').checked
    // console.log(data.isDisability)
    baggageCount = $("#baggageSum").val(); // 获取隐藏字段的值
    baggages = document.getElementById('materialForm')
    // 创建一个 FormData 对象来存储表单数据
    var formData = new FormData(baggages);
// 将 FormData 对象转换为一个对象
    var formDataObject = {};
    formData.forEach(function(value, key){
        formDataObject[key] = value;
    });
    console.log(formDataObject)
    data.normalBaggage = [];
    data.specialBaggage = [];
    // 可免费托运的特殊行李
    let freeSpecialBaggage1 = ['手动轮椅', '电动轮椅'],
        freeSpecialBaggage2 = ['婴儿车或摇篮'],
        freeSpecialBaggage3 = ['导盲犬', '骨灰'];
    // 运动器械器具
    let sportsSpecialBaggage1 = ['自行车'],
        sportsSpecialBaggage2 = ['皮划艇'],
        sportsSpecialBaggage3 = ['撑杆'];
    // 其他类型
    let othersSpecialBaggage1 = ['睡袋'],
        othersSpecialBaggage2 = ['小型电器或仪器'],
        othersSpecialBaggage3 = ['可作为行李运输的枪支'],
        othersSpecialBaggage4 = ['可作为行李运输的弹药'],
        othersSpecialBaggage5 = ['小动物'];

    for (let i = 1, cnt = 0; i < 50; i++) {
        let type = formDataObject['baggageType' + i];
        // 普通行李，或是按照普通行李类型收费的特殊行李
        if (type === '普通行李' || sportsSpecialBaggage1.includes(type) || othersSpecialBaggage1.includes(type)) {
            data.normalBaggage.push(createBaggage(i, formDataObject));
            cnt++;
        }
        // 非残疾和非婴儿旅客托运 轮椅或婴儿床,非残疾托运婴儿车，婴儿托运轮椅，按普通行李收费
        else if ((!(data.isDisability) && data.peopleType !== 'infant') && (freeSpecialBaggage1.includes(type) || freeSpecialBaggage2.includes(type))) {
            // console.log("~")
            data.normalBaggage.push(createBaggage(i, formDataObject));
            cnt++;
        }
        else if ((data.isDisability && data.peopleType !== 'infant' && freeSpecialBaggage2.includes(type)) || (data.peopleType === 'infant' && !data.isDisability && freeSpecialBaggage1.includes(type))){
            data.normalBaggage.push(createBaggage(i, formDataObject));
            // console.log("~")
            cnt++;
        }
        // 其余类型的特殊行李
        else if (type) {
            data.specialBaggage.push(createBaggage(i, formDataObject));
            cnt++;
        }
        if (cnt >= baggageCount) {
            break;
        }
    }
    message=document.getElementById('message1')
    message_box=document.getElementById('message-box')
    $.ajax({
        async: true,
        cache: false,
        type: 'POST',
        data: JSON.stringify(data),
        datType: "json",
        accept: "application/json;charset=UTF-8",
        contentType: "application/json;charset=UTF-8",
        url: '/baggage/calculate',
        success: function (data) {
            // alert(data.price);
            console.log(data.price)

            message.innerHTML="行李托运的价格为："+data.price+"元<br>" + data.message
            message_box.style.display = "block";
        },
        error: function (data) {
            alert('温馨提示', '!!', 'error');
        }
    });
}