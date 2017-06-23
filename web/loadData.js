/**
 * Created by Сергій on 13.06.2017.
 */
var GridTeacher;
var GridLerner;
var id=0;
function doOnLoad(){
    GridTeacher = new dhtmlXGridObject('gridboxTeacher');
    GridTeacher.setImagePath("../../../codebase/imgs/");
    GridTeacher.setHeader("#, Name teacher, count of teacher");
    GridTeacher.setInitWidths("70,250,*");
    GridTeacher.setColAlign("right,left,left");
   // GridTeacher.setColTypes("ed,ed,ed");
    GridTeacher.setColTypes("ro,ro,ro");
    GridTeacher.setColSorting("int,str,int");
    GridTeacher.setColumnIds("id,name,countOfLerner");
    GridTeacher.init();

    GridLerner = new dhtmlXGridObject('gridboxLerner');
    GridLerner.setImagePath("../../../codebase/imgs/");
    GridLerner.setHeader("#, name of lerner, teacher");
    GridLerner.setInitWidths("70,250,*");
    GridLerner.setColAlign("right,left,left");
 //   GridLerner.setColTypes("ed,ed,co");
    GridLerner.setColTypes("ro,ro,ro");
    GridLerner.setColSorting("int,str,str");
    GridLerner.setColumnIds("id,name,teacher");
  //  addToCombobox(/*"vas"*/);
    GridLerner.init();


    $.ajax({
        url: 'Servlet',
        type: "POST",
        dataType: "json",
        data: {method: "showAll",object: "teacher"},
        success :function(data){
            $.each(data,function (key,value) {
                addToCombobox(value.name);
                GridTeacher.addRow(value.id,[value.id,value.name,value.countOfLerner]);
                id=value.id;
                id++;


    $.ajax({
        url: 'Servlet',
        type: "POST",
        dataType: "json",
        data: {method: "showAll",object: "lerner"},
        success :function(data){
            $.each(data,function (key,value) {
             GridLerner.addRow(value.id,[value.id,value.name,value.teacher]);
            //id++;
        })
        }
    });})
}
});
    return false;
}


function addToCombobox(teacher) {
    var combobox = GridLerner.getCombo(2);
    combobox.put(teacher,teacher);
    $('#teacher').append($('<option>', {
        value: teacher,
        text: teacher
    }));

}


addTeacher = function(){
     var name=$('#teacherName').val();
    $.ajax({
        url: 'Servlet',
        type: "POST",
        dataType: "json",
        data: {data: name,method: "add",object: "teacher"},
        success :function(data){
            if(data.alert!=undefined)
                alert(data.alert);
            else {
                GridTeacher.addRow(data.id, [data.id, data.name, data.countOfLerner]);
                id++;
                addToCombobox(name);
            }
        }
    });
    return false;
};

addLerner = function(){
    var teacher = $('#teacher').val();
    var name=$('#lernerName').val();
    var count = GridLerner.getRowsNum();
    var rowID=GridTeacher.getRowId(0);
    var data1 = GridTeacher.getRowData(rowID);
    $.ajax({
        url: 'Servlet',
        type: "POST",
        dataType: "json",
        data: {teacher: teacher,name:name,method: "add",object: "lerner"},
        success :function(data){
            if(data.alert!=undefined)
                alert(data.alert);
            else {
              GridLerner.addRow(data.id,[data.id,data.name,data.teacher]);
             for (var i=0; i<GridTeacher.getRowsNum(); i++)
             {
                if (GridTeacher.getRowData(GridTeacher.getRowId(i)).name == teacher) {
                    data1 = GridTeacher.getRowData(GridTeacher.getRowId(i));
                    data1.countOfLerner = parseInt(GridTeacher.getRowData(GridTeacher.getRowId(i)).countOfLerner) + 1;
                    GridTeacher.setRowData(GridTeacher.getRowId(i), data1);
                   }
            }
         }
        }

    });
    return false;
};

