

const { Buffer } = require("buffer");
let fs = require("fs");
// let path = "/Users/uer7e67/Desktop/a_diwang.sanguo_0100010000_7054_v1.57.0520/assets/data/";
let path = 'D:/MyProject/dddd/assets/data/';

/**转换成完整png文件 */

function transformPng(fileName) {
    let filepath = path + fileName;
    let buf = fs.readFileSync(filepath);
    let buf1 = Buffer.from([0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a, 0x00, 0x00, 0x00, 0x0d])
    let buf2 = Buffer.from([0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, 0xAE, 0x42, 0x60, 0x82])
    let end = 0;
    for (let i = 0; i < buf.length; i++) {
        if (buf[i] == 0x49
            && buf[i + 1] == 0x48
            && buf[i + 2] == 0x44
            && buf[i + 3] == 0x52) {
            end = i;
            break;
        }
    }
    buf = buf.slice(end);
    let total = buf1.length + buf.length + buf2.length
    var final = Buffer.concat([buf1, buf, buf2], total)
    fs.writeFileSync(filepath, final);
}

/**批量改名字 */
function rename(files) {
    var arr = files.split('.');
    var houzhui = arr[arr.length - 1];
    if (houzhui == "dat") {
        fs.renameSync(path + files, path + arr[0] + "." + "png")
    }
}

/**写文件 */
fs.readdir(path, (err, files) => {
    console.log("file length ===", files.length)
    if (err) {
        console.log(err);
    } else {
        for (let i = 0; i < files.length; i++) {
            console.log("filees: =======>>>>>>>", files[i])
            transformPng(files[i]);
            rename(files[i]);
        }
    }
})


// for i in *.dat; do mv “$i” “${i%.dat}.png”