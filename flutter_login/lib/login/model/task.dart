import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class Task {
  String id;
  String name;
  String avatar;
  String createdAt;

  Task({this.id, this.name, this.avatar, this.createdAt});

  factory Task.fromJson(Map<String, dynamic> json) {
    return Task(
      id: json['id'] as String,
      name: json['name'] as String,
      avatar: json['avatar'] as String,
      createdAt: json['createdAt'] as String,
    );
  }

  Map<String, dynamic> toJson(Task instance) {
    return <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'avatar': instance.avatar,
      'createdAt': instance.createdAt,
    };
  }
}
