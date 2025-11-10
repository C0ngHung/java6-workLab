// Firebase Realtime Database host URL
const host = "https://poly-java-6-e08ab-default-rtdb.asia-southeast1.firebasedatabase.app/";

// API object để quản lý CRUD operations cho Student
const $api = {
    // Key để lưu trữ key của student đang được chỉnh sửa
    key: null,

    /**
     * Getter: Đọc dữ liệu từ form và trả về object student
     * @returns {Object} Student object với id, name, mark, gender
     */
    get student() {
        return {
            id: $("#id").val(),
            name: $("#name").val(),
            mark: $("#mark").val(),
            gender: $("#male").prop("checked")
        };
    },

    /**
     * Setter: Hiển thị dữ liệu student lên form
     * @param {Object} e - Student object cần hiển thị
     */
    set student(e) {
        if (!e) {
            e = {};
        }
        $("#id").val(e.id || "");
        $("#name").val(e.name || "");
        $("#mark").val(e.mark || "");
        $("#male").prop("checked", e.gender === true);
        $("#female").prop("checked", e.gender === false);
    },

    /**
     * Tải và hiển thị danh sách sinh viên lên bảng
     */
    fillToTable() {
        const url = `${host}/students.json`;
        axios.get(url)
            .then(resp => {
                $("tbody").empty();
                
                // Kiểm tra nếu không có dữ liệu
                if (!resp.data) {
                    return;
                }

                Object.keys(resp.data).forEach(key => {
                    const e = resp.data[key];
                    const tr = `
                        <tr>
                            <td>${e.id || ""}</td>
                            <td>${e.name || ""}</td>
                            <td>${e.mark || ""}</td>
                            <td>${e.gender ? 'Male' : 'Female'}</td>
                            <td>
                                <a href="#" onclick="$api.edit('${key}'); return false;">Edit</a> |
                                <a href="#" onclick="$api.delete('${key}'); return false;">Delete</a>
                            </td>
                        </tr>
                    `;
                    $("tbody").append(tr);
                });
            })
            .catch(error => {
                console.error("Lỗi tải danh sách sinh viên:", error);
                alert("Lỗi tải danh sách sinh viên!");
            });
    },

    /**
     * Tải sinh viên theo key và hiển thị lên form để chỉnh sửa
     * @param {string} key - Key của student trong Firebase
     */
    edit(key) {
        if (!key) {
            alert("Key không hợp lệ!");
            return;
        }

        this.key = key.trim();
        const url = `${host}/students/${this.key}.json`;
        
        axios.get(url)
            .then(resp => {
                if (resp.data) {
                    this.student = resp.data;
                } else {
                    alert("Không tìm thấy sinh viên!");
                }
            })
            .catch(error => {
                console.error("Lỗi tải sinh viên:", error);
                alert("Lỗi tải sinh viên!");
            });
    },

    /**
     * Thêm sinh viên mới vào Firebase
     */
    create() {
        const studentData = this.student;
        
        // Validation cơ bản
        if (!studentData.id || !studentData.name) {
            alert("Vui lòng nhập đầy đủ thông tin (ID và Tên)!");
            return;
        }

        const url = `${host}/students.json`;
        axios.post(url, studentData)
            .then(resp => {
                alert("Thêm sinh viên thành công!");
                this.fillToTable();
                this.reset();
            })
            .catch(error => {
                console.error("Lỗi thêm sinh viên mới:", error);
                alert("Lỗi thêm sinh viên mới!");
            });
    },

    /**
     * Cập nhật thông tin sinh viên đã tồn tại
     */
    update() {
        if (!this.key) {
            alert("Vui lòng chọn sinh viên cần cập nhật!");
            return;
        }

        const studentData = this.student;
        
        // Validation cơ bản
        if (!studentData.id || !studentData.name) {
            alert("Vui lòng nhập đầy đủ thông tin (ID và Tên)!");
            return;
        }

        const url = `${host}/students/${this.key}.json`;
        axios.put(url, studentData)
            .then(resp => {
                alert("Cập nhật sinh viên thành công!");
                this.fillToTable();
                this.reset();
            })
            .catch(error => {
                console.error("Lỗi cập nhật sinh viên:", error);
                alert("Lỗi cập nhật sinh viên!");
            });
    },

    /**
     * Xóa sinh viên theo key
     * @param {string} key - Key của student cần xóa (optional, nếu không có sẽ dùng this.key)
     */
    delete(key) {
        const deleteKey = key || this.key;
        
        if (!deleteKey) {
            alert("Vui lòng chọn sinh viên cần xóa!");
            return;
        }

        if (!confirm("Bạn có chắc chắn muốn xóa sinh viên này?")) {
            return;
        }

        const url = `${host}/students/${deleteKey}.json`;
        axios.delete(url)
            .then(resp => {
                alert("Xóa sinh viên thành công!");
                this.fillToTable();
                this.reset();
            })
            .catch(error => {
                console.error("Lỗi xóa sinh viên:", error);
                alert("Lỗi xóa sinh viên!");
            });
    },

    /**
     * Xóa trắng form và reset key
     */
    reset() {
        this.key = null;
        this.student = {};
    }
};

// Tự động tải danh sách sinh viên khi script được load
$api.fillToTable();
