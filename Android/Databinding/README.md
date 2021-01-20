# DataBinding 예제

## ⚡ Features
* Base패턴을 사용하여 Activity, Fragment, View, Dialog, Holder등 바인딩 객체를 얻는 법을 사용했습니다. (https://rkdxowhd98.tistory.com/113)

* 바인딩 수식 : import, bind, default 등 바인딩 수식을 사용하여 Java와 Kotlin으로 View를 업데이트 하는 코드를 최소화 하였습니다. (https://rkdxowhd98.tistory.com/114)

* BindingAdapter : 바인딩 수식에서 제공하지 않는 것들을 BindingAdapter를 통해 Binding Function을 만들어서 Java와 Kotlin에서 View를 업데이트 하는 코드를 최소화 했습니다. (https://rkdxowhd98.tistory.com/115)

* Glide : ImageUrl을 통해 ImageView를 업데이트하는 라이브러리

## 😊 Introduction
### MovieFragment
* #### RecyclerView StaggeredGridLayoutManager를 사용하여 영화 포스터마다 높이가 달라도 자연스럽게 표시하였습니다.
* #### Glide를 사용하여 영화의 포스터를 ImageView에 업데이트하였습니다.
* #### BindingAdapter를 사용하여 Glide를 Databinding으로 처리했습니다.
<img src="./readme/MovieFragment.png" alt="MovieFragment" width="30%">

### InformationFragment
* #### navigation action을 통해 movie객체를 전달하고 Databinding을 통해서 UI를 업데이트 했습니다.
* #### movie는 Parcelable를 상속받아 구현하여 전달할 수 있게 만들었습니다.
<img src="./readme/InformationFragment.png" alt="InformationFragment" width="30%">

