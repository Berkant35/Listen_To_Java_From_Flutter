

import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  StreamController<List<Object?>> controller = StreamController<List<Object?>>.broadcast();

  late Stream stream;
  final Widget goodJob = const Text('Good job!');
  var eventChannel = const EventChannel("streamData");

  @override
  void initState() {
    stream = controller.stream;
    stream.listen((value) {
      debugPrint('$value');
    });
    initalizeOfChannelse();

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StreamBuilder<List<Object?>>(
        stream: controller.stream,
        builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
          List<Object?>? listOfData = snapshot.data;
          return listOfData != null && listOfData.isNotEmpty ? ListView.builder(
              itemCount: listOfData.length,
              itemBuilder: (context,index){
               return Text(listOfData[index].toString());
          }) : const Center(child: CircularProgressIndicator(),);
        },),
    );
  }

  Future<void> initalizeOfChannelse() async {
    eventChannel.receiveBroadcastStream("listenList").listen((event) {
      controller.add(event);
    });

  }


}
