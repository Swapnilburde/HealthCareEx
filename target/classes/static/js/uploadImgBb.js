function uploadFile() {
  var file = document.getElementById("fileOb");
  var form = new FormData();
  form.append("image", file.files[0]);
  var inputs = {
    url: "https://api.imgbb.com/1/upload?key=7b5de1ec25028e4275a2a46e3ac6a12c",
    method: "POST",
    timeout: 0,
    processData: false,
    mimeType: "multipart/form-data",
    contentType: false,
    data: form,
  };

  $.ajax(inputs).done(function (response) {
    var job = JSON.parse(response);
    $("#photoLoc").val(job.data.url);
    $("#photoLocError").html("Image Uploaded");
    $("#photoLocError").css('color','green');
  });
}