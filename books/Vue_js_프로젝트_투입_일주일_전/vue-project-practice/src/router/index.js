import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DataBinding from "@/views/DataBinding";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/databinding',
    name: 'DataBinding',
    component: DataBinding
  },
  {
    path: '/databindinghtml',
    name: 'DataBindingHtml',
    component: () => import( /* webpackChunkName: "databindinghtml" */ '../views/DataBindingHtml.vue')
  },
  {
    path: '/databindinginputtext',
    name: 'DataBindingInputText',
    component: () => import( /* webpackChunkName: "databindinginputtext" */ '../views/DataBindingInputText.vue')
  },
  {
    path: '/databindinginputnumber',
    name: 'DataBindingInputNumber',
    component: () => import( /* webpackChunkName: "databindinginputnumber" */ '../views/DataBindingInputNumber.vue')
  },
  {
    path: '/databindinginputtextarea',
    name: 'DataBindingTextarea',
    component: () => import( /* webpackChunkName: "databindinginputtextarea" */ '../views/DataBindingTextarea.vue')
  },
  {
    path: '/databindingselect',
    name: 'DataBindingSelect',
    component: () => import( /* webpackChunkName: "databindingselect" */ '../views/DataBindingSelect.vue')
  },
  {
    path: '/databindingcheckbox',
    name: 'DataBindingCheckbox',
    component: () => import( /* webpackChunkName: "databindingcheckbox" */ '../views/DataBindingCheckbox.vue')
  },
  {
    path: '/databindingcheckbox2',
    name: 'DataBindingCheckbox2',
    component: () => import( /* webpackChunkName: "databindingcheckbox2" */ '../views/DataBindingCheckbox2.vue')
  },
  {
    path: '/databindingradio',
    name: 'DataBindingRadio',
    component: () => import( /* webpackChunkName: "databindingradio" */ '../views/DataBindingRadio.vue')
  },
  {
    path: '/databindingattribute',
    name: 'DataBindingAttribute',
    component: () => import( /* webpackChunkName: "databindingattribute" */ '../views/DataBindingAttribute.vue')
  },
  {
    path: '/databindingbutton',
    name: 'DataBindingButton',
    component: () => import( /* webpackChunkName: "databindingbutton" */ '../views/DataBindingButton.vue')
  },
  {
    path: '/databindingclass',
    name: 'DataBindingClass',
    component: () => import( /* webpackChunkName: "databindingclass" */ '../views/DataBindingClass.vue')
  },
  {
    path: '/databindingclass2',
    name: 'DataBindingClass2',
    component: () => import( /* webpackChunkName: "databindingclass2" */ '../views/DataBindingClass2.vue')
  },
  {
    path: '/databindingstyle',
    name: 'DataBindingStyle',
    component: () => import( /* webpackChunkName: "databindingstyle" */ '../views/DataBindingStyle.vue')
  },
  {
    path: '/databindingstyle2',
    name: 'DataBindingStyle2',
    component: () => import( /* webpackChunkName: "databindingstyle2" */ '../views/DataBindingStyle2.vue')
  },
  {
    path: '/databindinglist',
    name: 'DataBindingList',
    component: () => import( /* webpackChunkName: "databindinglist" */ '../views/DataBindingList.vue')
  },
  {
    path: '/renderingvif',
    name: 'RenderingVIf',
    component: () => import( /* webpackChunkName: "renderingvif" */ '../views/RenderingVIf.vue')
  },
  {
    path: '/eventclick',
    name: 'EventClick',
    component: () => import( /* webpackChunkName: "eventclick" */ '../views/EventClick.vue')
  },
  {
    path: '/eventchange',
    name: 'EventChange',
    component: () => import( /* webpackChunkName: "eventchange" */ '../views/EventChange.vue')
  },
  {
    path: '/computed',
    name: 'Computed',
    component: () => import( /* webpackChunkName: "computed" */ '../views/Computed.vue')
  },
  {
    path: '/watch',
    name: 'Watch',
    component: () => import( /* webpackChunkName: "watch" */ '../views/Watch.vue')
  },
  {
    path: '/watch2',
    name: 'Watch2',
    component: () => import( /* webpackChunkName: "watch2" */ '../views/Watch2.vue')
  },
  {
    path: '/databindinglist2',
    name: 'DataBindingList2',
    component: () => import( /* webpackChunkName: "databindinglist2" */ '../views/DataBindingList2.vue')
  },
  {
    path: '/nestedcomponent',
    name: 'NestedComponent',
    component: () => import( /* webpackChunkName: "nestedcomponent" */ '../views/NestedComponent.vue')
  },
  {
    path: '/parentcomponent',
    name: 'ParentComponent',
    component: () => import( /* webpackChunkName: "parentcomponent" */ '../views/ParentComponent.vue')
  },
  {
    path: '/parentcomponent2',
    name: 'ParentComponent2',
    component: () => import( /* webpackChunkName: "parentcomponent2" */ '../views/ParentComponent2.vue')
  },
  {
    path: '/parentcomponent3',
    name: 'ParentComponent3',
    component: () => import( /* webpackChunkName: "parentcomponent3" */ '../views/ParentComponent3.vue')
  },
  {
    path: '/parentcomponent4',
    name: 'ParentComponent4',
    component: () => import( /* webpackChunkName: "parentcomponent4" */ '../views/ParentComponent4.vue')
  },
  {
    path: '/parentcomponent5',
    name: 'ParentComponent5',
    component: () => import( /* webpackChunkName: "parentcomponent5" */ '../views/ParentComponent5.vue')
  },
  {
    path: '/slotusemodel',
    name: 'SlotUseModel',
    component: () => import( /* webpackChunkName: "slotusemodel" */ '../views/SlotUseModel.vue')
  },
  {
    path: '/provideinject',
    name: 'ProvideInject',
    component: () => import( /* webpackChunkName: "provideinject" */ '../views/ProvideInject.vue')
  },
  {
    path: '/calculator',
    name: 'Calculator',
    component: () => import( /* webpackChunkName: "calculator" */ '../views/Calculator.vue')
  },
  {
    path: '/compositionapi',
    name: 'CompositionAPI',
    component: () => import( /* webpackChunkName: "compositionapi" */ '../views/CompositionAPI.vue')
  },
  {
    path: '/compositionapi2',
    name: 'CompositionAPI2',
    component: () => import( /* webpackChunkName: "compositionapi2" */ '../views/CompositionAPI2.vue')
  },
  {
    path: '/compositionapi3',
    name: 'CompositionAPI3',
    component: () => import( /* webpackChunkName: "compositionapi3" */ '../views/CompositionAPI3.vue')
  },
  {
    path: '/compositionapi4',
    name: 'CompositionAPI4',
    component: () => import( /* webpackChunkName: "compositionapi4" */ '../views/CompositionAPI4.vue')
  },
  {
    path: '/compositionapiprovide',
    name: 'CompositionAPIProvide',
    component: () => import( /* webpackChunkName: "compositionapiprovide" */ '../views/CompositionAPIProvide.vue')
  },
  {
    path: '/mixins',
    name: 'Mixins',
    component: () => import( /* webpackChunkName: "mixins" */ '../views/Mixins.vue')
  },
  {
    path: '/storeaccess',
    name: 'StoreAccess',
    component: () => import( /* webpackChunkName: "storeaccess" */ '../views/StoreAccess.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
