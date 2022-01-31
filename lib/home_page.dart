

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final ValueNotifier<String> timerCounter = ValueNotifier<String>("0.0");
  final Widget goodJob = const Text('Good job!');
  var eventChannel = const EventChannel("streamData");
  @override
  void initState() {

    initalizeOfChannelse();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Row(
          children: [

            ValueListenableBuilder<String>(
              builder: (BuildContext context, String value, Widget? child) {
                // This builder will only get called when the _counter
                // is updated.
                return Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Text('Time: $value'),
                    child!,
                  ],
                );
              },
              valueListenable: timerCounter,
              // The child parameter is most helpful if the child is
              // expensive to build and does not depend on the value from
              // the notifier.
              child: goodJob,
            )
          ],
        ),
      ),
    );
  }

  Future<void> initalizeOfChannelse() async {
    eventChannel.receiveBroadcastStream("listenList").listen((event) {
      timerCounter.value = event;
    });
  }
}
