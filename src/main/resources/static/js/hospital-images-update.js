let fileNo = 0;
let filesArr = new Array();

function addFile(obj) {
    let maxFileCnt = 3;
    let attFileCnt = document.querySelectorAll(".filebox").length;
    let remainFileCnt = maxFileCnt - attFileCnt;
    let curFileCnt = obj.files.length;

    if (curFileCnt > remainFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");

        $('#hospitalImage').val('');
    }

    // 파일 리스트를 담을 변수
    let fileList = document.querySelector(".file-list");
    // 선택된 파일 없음 메시지를 담을 변수
    let selectedFilesLabel = document.querySelector(".selected-files-label");

    for (let i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {
        const file = obj.files[i];

        if (validation(file)) {
            let reader = new FileReader();
            reader.onload = function (e) {
                // 이미지 파일인 경우에만 미리보기 추가
                if (file.type.match("image.*")) {
                    let htmlData =`<div id="file${fileNo}" class="filebox">
                                                <img src="${e.target.result}" alt="file-preview" />
                                                <p class="name">${file.name}</p>
                                                <a class="delete-btn" onclick="deleteFile(${fileNo});">
                                                    <i class="far fa-minus-square"></i>
                                                </a>
                                            </div>`;

                    $(".file-list").append(htmlData);
                }
                // 파일 배열에 담기
                filesArr.push(file);
                fileNo++;
            };
            reader.readAsDataURL(file);
        }
    }
    if (fileList.children.length > 0) {
        selectedFilesLabel.style.display = "none";
    } else {
        selectedFilesLabel.style.display = "block";
    }

    $('#hospitalImage').val('');
}

function validation(obj) {
    const fileTypes = ["image/jpeg", "image/png", "image/bmp", "image/jpg"];

    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    }
    if (obj.size > 100 * 1024 * 1024) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    }
    if (obj.name.lastIndexOf(".") == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    }
    if (!fileTypes.includes(obj.type)) {
        alert("첨부가 불가능한 파일은 제외되었습니다.");
        return false;
    }

    return true;
}

function deleteFile(num) {
    document.querySelector("#file" + num).remove();
    filesArr[num].is_delete = true;
}

function deleteOriginFile(index, hospitalImageSeq) {
    let result = confirm("삭제하시겠습니까?");
    if (result) {
        $.ajax({
            method: "PATCH",
            url: "/hospital/images/form",
            data: {
                "hospitalImageSeq": hospitalImageSeq
            },
            timeout: 30000,
            cache: false,
            headers: {"cache-control": "no-cache", pragma: "no-cache"},
            success: function (result) {
                alert("병원 이미지가 삭제되었습니다.");
                document.getElementById('file' + index + '_' + hospitalImageSeq).remove();
            },
            error: function (xhr, desc, err) {
                alert("에러가 발생 하였습니다.");
                return;
            },
        });
    } else {
        alert("삭제가 취소되었습니다.");
    }

}

