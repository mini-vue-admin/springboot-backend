import request from "@/utils/request.js"

export function getPage(query) {
    return request.get("${MODULE_NAME}/${ENTITY_FIELD_NAME}", {
        params: query
    })
}

export function getById(id) {
    return request.get("${MODULE_NAME}/${ENTITY_FIELD_NAME}/" + id)
}

export function create(data) {
    return request.post("${MODULE_NAME}/${ENTITY_FIELD_NAME}", data)
}

export function update(data) {
    return request.put("${MODULE_NAME}/${ENTITY_FIELD_NAME}", data)
}

export function del(id) {
    if (Array.isArray(id)) {
        if (id.length > 1) {
            return request.delete("${MODULE_NAME}/${ENTITY_FIELD_NAME}", {
                params: {id: id.join(",")}
            })
        } else {
            return request.delete(`${MODULE_NAME}/${ENTITY_FIELD_NAME}/${r'${id[0]}'}`)
        }
    } else {
        return request.delete(`${MODULE_NAME}/${ENTITY_FIELD_NAME}/${r'${id}'}`)
    }
}