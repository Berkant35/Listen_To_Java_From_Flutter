import 'package:flutter/material.dart';

import 'home_page.dart';

void main() => runApp(MyApp());


enum ViewState {LOADING,LOADED}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  ViewState viewState = ViewState.LOADED;

  @override
  void initState() {
    viewState = ViewState.LOADING;
    Future.delayed(const Duration(seconds: 10),(){
      viewState = ViewState.LOADED;
      setState(() {});
    });
    super.initState();
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {


    return MaterialApp(
      title: 'Material App',
      home:  Scaffold(
        appBar: AppBar(
          title: Text('Material App Bar'),
        ),
        body: HomePage(),
      ),
    );
  }
}
