<template>
  <div class="flex flex-col gap-2.5 p-5">
    <form class="flex flex-wrap gap-3.5">
      <FloatLabel>
        <InputText id="username" type="search" v-model="queryParams.username" @keyup.enter.native="handleQuery"/>
        <label for="username">用户名</label>
      </FloatLabel>

      <Button label="查询" icon="pi pi-search" aria-label="Search" @click="handleQuery"/>
    </form>

    <div class="flex flex-wrap gap-5">
      <Button label="创建" class="w-20" @click="handleAdd"/>
      <Button label="删除" class="w-20" severity="danger" @click="handleDelete()"/>
    </div>

    <DataTable :value="tableData.list"
               v-model:selection="tableData.selection"
               :lazy="true"
               tableStyle="min-width: 50rem"
               :rows="queryParams.pageSize"
               :loading="tableData.loading"
               @sort="handleQuery" sort-field="updateTime" :sort-order="-1"
               selectionMode="multiple"
               dataKey="id"
               :pt="{
                  bodyRow: {
                    style:'cursor:default'
                  }
                }">
      <Column selectionMode="multiple" header-style="width: 3rem"/>
  <#list FIELDS as f>
      <Column field="${f.FIELD_NAME}" header="${f.FIELD_SHORT_COMMENT}"></Column>
  </#list>
      <Column field="createTime" header="创建时间" sortable header-style="width: 13rem"></Column>
      <Column field="updateTime" header="修改时间" sortable header-style="width: 13rem"></Column>
      <Column header="操作" header-style="width: 10rem">
        <template #body="slotProps">
          <Button text label="修改" @click.stop="handleUpdate(slotProps.data)"/>
          <Button text label="删除" severity="danger" @click.stop="handleDelete(slotProps.data)"/>
        </template>
      </Column>
    </DataTable>
    <paginator :rowsPerPageOptions="[10, 20, 50, 100]"
               :rows="queryParams.pageSize"
               :totalRecords="tableData.totalCount"
               :first="queryParams.pageSize * (queryParams.pageIndex - 1)"
               @page="handleQuery"
    />
  </div>

  <Drawer v-model:visible="formDialog.open" :header="formDialog.title" position="right" :dismissable="false"
          class="!w-full md:!w-80 lg:!w-[30rem]">
    <form class="flex flex-col gap-5 pt-2">
      <#list FIELDS as f>
      <FloatLabel variant="on">
          <InputText id="${f.FIELD_NAME}" fluid v-model="formData.${f.FIELD_NAME}"
                     :invalid="$v.${f.FIELD_NAME}.$error"
                     v-tooltip.top="$v.${f.FIELD_NAME}.$errors.map(it=>it.$message).join('\n')"
          />
          <label for="${f.FIELD_NAME}">${f.FIELD_SHORT_COMMENT}</label>
      </FloatLabel>
      </#list>

      <div class="flex flex-wrap justify-center gap-5">
        <Button label="提交" aria-label="Submit" @click="handleSubmit"/>
        <Button label="取消" aria-label="Cancel" severity="secondary" @click="formDialog.open = false"/>
      </div>
    </form>
  </Drawer>

</template>
<script setup>

import {create, del, getById, getPage, update} from "@/api/${MODULE_NAME}/${ENTITY_FIELD_NAME}.js";
import {computed, reactive, ref} from "vue";
import {SORT_ORDER} from "@/utils/cons.js";
import {useConfirm} from "primevue/useconfirm";
import {useToast} from "primevue/usetoast";
import {dicts} from "@/api/system/dicts.js";
import {required} from '@/utils/i18n-validators';
import {useVuelidate} from '@vuelidate/core';

const confirm = useConfirm();
const toast = useToast();

const queryParams = reactive({
  pageIndex: 1,
  pageSize: 10,
  sortField: 'updateTime',
  sortOrder: 'DESC',
});

const tableData = reactive({
  list: [],
  totalCount: 0,
  loading: false,
  selection: []
});
const formData = ref({});
const formDialog = reactive({
  title: null,
  open: false
});
const rules = computed(() => ({
    <#list FIELDS as f>
    ${f.FIELD_NAME}:{},
    </#list>
}));

const $v = useVuelidate(rules, formData, {$lazy: true});

handleQuery();

function handleQuery(pageState) {
  tableData.loading = true;
  if (pageState) {
    queryParams.pageSize = pageState.rows ?? queryParams.pageSize
    queryParams.pageIndex = pageState.page == null ? queryParams.pageIndex : pageState.page + 1
    queryParams.sortField = pageState.sortField ?? queryParams.sortField
    queryParams.sortOrder = SORT_ORDER.of(pageState.sortOrder) ?? queryParams.sortOrder
  }

  return getPage(queryParams).then(data => {
    const {list, pageSize, totalCount, pageIndex} = data;
    tableData.list = list;
    tableData.pageSize = pageSize;
    tableData.pageIndex = pageIndex;
    tableData.totalCount = totalCount;
    tableData.loading = false;
    tableData.selection = [];
  });
}

function handleAdd() {
  formDialog.open = true;
  formDialog.title = "创建${TABLE_COMMENT}";
  resetForm();
}

function handleUpdate(data) {
  tableData.selection = [data]
  resetForm();
  getById(data.id).then(data => {
    formData.value = data
    formDialog.open = true;
    formDialog.title = "修改${TABLE_COMMENT}"
  })
}

function resetForm() {
  formData.value = {

  }
  $v.value.$reset();
}

function handleSubmit() {
  const result = $v.value.$validate();
  result.then((res) => {
    if (res) {
      if (formData.value.id != null) {
        update(formData.value).then(() => {
          formDialog.open = false;
          handleQuery();
          toast.add({severity: 'success', summary: '消息', detail: '操作成功', life: 1350});
        });
      } else {
        create(formData.value).then(() => {
          formDialog.open = false;
          handleQuery();
          toast.add({severity: 'success', summary: '消息', detail: '操作成功', life: 1350});
        })
      }
    }
  })

}

function handleDelete(data) {
  if (data) {
    tableData.selection = [data]
  }
  const ids = tableData.selection.map(it => it.id)
  if (!ids || ids.length === 0) {
    toast.add({severity: 'warn', summary: '消息', detail: '请选择至少一条数据', life: 3000});
    return
  }
  confirm.require({
    header: '确认',
    message: '确认删除选中的数据吗?',
    icon: 'pi pi-exclamation-triangle',
    rejectProps: {
      label: '取消',
      severity: 'secondary',
      outlined: true
    },
    acceptProps: {
      label: '确认'
    },
    accept: () => {
      del(ids)
          .then(() => {
            tableData.selection = [];
            toast.add({severity: 'success', summary: '消息', detail: '删除成功', life: 3000});
            handleQuery()
          })
    }
  });
}

</script>
