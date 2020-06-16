import 'package:flutter/material.dart';

class InvListVo extends ChangeNotifier{
  int _code;
  String _message;
  Data _data;
  int _serviceTime;
  bool _success;

  InvListVo(
      {int code, String message, Data data, int serviceTime, bool success}) {
    this._code = code;
    this._message = message;
    this._data = data;
    this._serviceTime = serviceTime;
    this._success = success;
  }

  int get code => _code;
  set code(int code) => _code = code;
  String get message => _message;
  set message(String message) => _message = message;
  Data get data => _data;
  set data(Data data) => _data = data;
  int get serviceTime => _serviceTime;
  set serviceTime(int serviceTime) => _serviceTime = serviceTime;
  bool get success => _success;
  set success(bool success) => _success = success;

  InvListVo.fromJson(Map<String, dynamic> json) {
    _code = json['code'];
    _message = json['message'];
    _data = json['data'] != null ? new Data.fromJson(json['data']) : null;
    _serviceTime = json['serviceTime'];
    _success = json['success'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['code'] = this._code;
    data['message'] = this._message;
    if (this._data != null) {
      data['data'] = this._data.toJson();
    }
    data['serviceTime'] = this._serviceTime;
    data['success'] = this._success;
    return data;
  }
}

class Data {
  InvitationList _invitationList;
  int _wishCount;
  int _giftCount;
  int _gusetCount;

  Data(
      {InvitationList invitationList,
        int wishCount,
        int giftCount,
        int gusetCount}) {
    this._invitationList = invitationList;
    this._wishCount = wishCount;
    this._giftCount = giftCount;
    this._gusetCount = gusetCount;
  }

  InvitationList get invitationList => _invitationList;
  set invitationList(InvitationList invitationList) =>
      _invitationList = invitationList;
  int get wishCount => _wishCount;
  set wishCount(int wishCount) => _wishCount = wishCount;
  int get giftCount => _giftCount;
  set giftCount(int giftCount) => _giftCount = giftCount;
  int get gusetCount => _gusetCount;
  set gusetCount(int gusetCount) => _gusetCount = gusetCount;

  Data.fromJson(Map<String, dynamic> json) {
    _invitationList = json['invitationList'] != null
        ? new InvitationList.fromJson(json['invitationList'])
        : null;
    _wishCount = json['wishCount'];
    _giftCount = json['giftCount'];
    _gusetCount = json['gusetCount'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this._invitationList != null) {
      data['invitationList'] = this._invitationList.toJson();
    }
    data['wishCount'] = this._wishCount;
    data['giftCount'] = this._giftCount;
    data['gusetCount'] = this._gusetCount;
    return data;
  }
}

class InvitationList {
  int _pageNum;
  int _pageSize;
  int _size;
  Null _orderBy;
  int _startRow;
  int _endRow;
  String _total;
  int _pages;
  List<ListVo> _list;
  int _firstPage;
  int _prePage;
  int _nextPage;
  int _lastPage;
  bool _isFirstPage;
  bool _isLastPage;
  bool _hasPreviousPage;
  bool _hasNextPage;
  int _navigatePages;
  List<int> _navigatepageNums;

  InvitationList(
      {int pageNum,
        int pageSize,
        int size,
        Null orderBy,
        int startRow,
        int endRow,
        String total,
        int pages,
        List<ListVo> list,
        int firstPage,
        int prePage,
        int nextPage,
        int lastPage,
        bool isFirstPage,
        bool isLastPage,
        bool hasPreviousPage,
        bool hasNextPage,
        int navigatePages,
        List<int> navigatepageNums}) {
    this._pageNum = pageNum;
    this._pageSize = pageSize;
    this._size = size;
    this._orderBy = orderBy;
    this._startRow = startRow;
    this._endRow = endRow;
    this._total = total;
    this._pages = pages;
    this._list = list;
    this._firstPage = firstPage;
    this._prePage = prePage;
    this._nextPage = nextPage;
    this._lastPage = lastPage;
    this._isFirstPage = isFirstPage;
    this._isLastPage = isLastPage;
    this._hasPreviousPage = hasPreviousPage;
    this._hasNextPage = hasNextPage;
    this._navigatePages = navigatePages;
    this._navigatepageNums = navigatepageNums;
  }

  int get pageNum => _pageNum;
  set pageNum(int pageNum) => _pageNum = pageNum;
  int get pageSize => _pageSize;
  set pageSize(int pageSize) => _pageSize = pageSize;
  int get size => _size;
  set size(int size) => _size = size;
  Null get orderBy => _orderBy;
  set orderBy(Null orderBy) => _orderBy = orderBy;
  int get startRow => _startRow;
  set startRow(int startRow) => _startRow = startRow;
  int get endRow => _endRow;
  set endRow(int endRow) => _endRow = endRow;
  String get total => _total;
  set total(String total) => _total = total;
  int get pages => _pages;
  set pages(int pages) => _pages = pages;
  List<ListVo> get list => _list;
  set list(List<ListVo> list) => _list = list;
  int get firstPage => _firstPage;
  set firstPage(int firstPage) => _firstPage = firstPage;
  int get prePage => _prePage;
  set prePage(int prePage) => _prePage = prePage;
  int get nextPage => _nextPage;
  set nextPage(int nextPage) => _nextPage = nextPage;
  int get lastPage => _lastPage;
  set lastPage(int lastPage) => _lastPage = lastPage;
  bool get isFirstPage => _isFirstPage;
  set isFirstPage(bool isFirstPage) => _isFirstPage = isFirstPage;
  bool get isLastPage => _isLastPage;
  set isLastPage(bool isLastPage) => _isLastPage = isLastPage;
  bool get hasPreviousPage => _hasPreviousPage;
  set hasPreviousPage(bool hasPreviousPage) =>
      _hasPreviousPage = hasPreviousPage;
  bool get hasNextPage => _hasNextPage;
  set hasNextPage(bool hasNextPage) => _hasNextPage = hasNextPage;
  int get navigatePages => _navigatePages;
  set navigatePages(int navigatePages) => _navigatePages = navigatePages;
  List<int> get navigatepageNums => _navigatepageNums;
  set navigatepageNums(List<int> navigatepageNums) =>
      _navigatepageNums = navigatepageNums;

  InvitationList.fromJson(Map<String, dynamic> json) {
    _pageNum = json['pageNum'];
    _pageSize = json['pageSize'];
    _size = json['size'];
    _orderBy = json['orderBy'];
    _startRow = json['startRow'];
    _endRow = json['endRow'];
    _total = json['total'];
    _pages = json['pages'];
    if (json['list'] != null) {
      _list = new List<ListVo>();
      json['list'].forEach((v) {
        _list.add(new ListVo.fromJson(v));
      });
    }
    _firstPage = json['firstPage'];
    _prePage = json['prePage'];
    _nextPage = json['nextPage'];
    _lastPage = json['lastPage'];
    _isFirstPage = json['isFirstPage'];
    _isLastPage = json['isLastPage'];
    _hasPreviousPage = json['hasPreviousPage'];
    _hasNextPage = json['hasNextPage'];
    _navigatePages = json['navigatePages'];
    _navigatepageNums = json['navigatepageNums'].cast<int>();
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['pageNum'] = this._pageNum;
    data['pageSize'] = this._pageSize;
    data['size'] = this._size;
    data['orderBy'] = this._orderBy;
    data['startRow'] = this._startRow;
    data['endRow'] = this._endRow;
    data['total'] = this._total;
    data['pages'] = this._pages;
    if (this._list != null) {
      data['list'] = this._list.map((v) => v.toJson()).toList();
    }
    data['firstPage'] = this._firstPage;
    data['prePage'] = this._prePage;
    data['nextPage'] = this._nextPage;
    data['lastPage'] = this._lastPage;
    data['isFirstPage'] = this._isFirstPage;
    data['isLastPage'] = this._isLastPage;
    data['hasPreviousPage'] = this._hasPreviousPage;
    data['hasNextPage'] = this._hasNextPage;
    data['navigatePages'] = this._navigatePages;
    data['navigatepageNums'] = this._navigatepageNums;
    return data;
  }
}

class ListVo {
  String _invitationId;
  String _cover;
  int _auditStatus;
  String _auditDesc;
  int _share;
  int _coverShowWidth;
  int _coverShowHeight;
  int _musicVideo;
  int _draft;
  String _invationUrl;
  String _mvUrl;
  Null _weddingBoy;
  Null _weddingGirl;
  Null _weddingDate;
  Null _weddingAddress;
  Null _weddingLng;
  Null _weddingLat;
  Null _themeId;
  int _invitationShowStatus;
  int _invitationShowStatusNew;
  int _canCreateAgain;
  int _feedbackShowFlag;

  List(
      {String invitationId,
        String cover,
        int auditStatus,
        String auditDesc,
        int share,
        int coverShowWidth,
        int coverShowHeight,
        int musicVideo,
        int draft,
        String invationUrl,
        String mvUrl,
        Null weddingBoy,
        Null weddingGirl,
        Null weddingDate,
        Null weddingAddress,
        Null weddingLng,
        Null weddingLat,
        Null themeId,
        int invitationShowStatus,
        int invitationShowStatusNew,
        int canCreateAgain,
        int feedbackShowFlag}) {
    this._invitationId = invitationId;
    this._cover = cover;
    this._auditStatus = auditStatus;
    this._auditDesc = auditDesc;
    this._share = share;
    this._coverShowWidth = coverShowWidth;
    this._coverShowHeight = coverShowHeight;
    this._musicVideo = musicVideo;
    this._draft = draft;
    this._invationUrl = invationUrl;
    this._mvUrl = mvUrl;
    this._weddingBoy = weddingBoy;
    this._weddingGirl = weddingGirl;
    this._weddingDate = weddingDate;
    this._weddingAddress = weddingAddress;
    this._weddingLng = weddingLng;
    this._weddingLat = weddingLat;
    this._themeId = themeId;
    this._invitationShowStatus = invitationShowStatus;
    this._invitationShowStatusNew = invitationShowStatusNew;
    this._canCreateAgain = canCreateAgain;
    this._feedbackShowFlag = feedbackShowFlag;
  }

  String get invitationId => _invitationId;
  set invitationId(String invitationId) => _invitationId = invitationId;
  String get cover => _cover;
  set cover(String cover) => _cover = cover;
  int get auditStatus => _auditStatus;
  set auditStatus(int auditStatus) => _auditStatus = auditStatus;
  String get auditDesc => _auditDesc;
  set auditDesc(String auditDesc) => _auditDesc = auditDesc;
  int get share => _share;
  set share(int share) => _share = share;
  int get coverShowWidth => _coverShowWidth;
  set coverShowWidth(int coverShowWidth) => _coverShowWidth = coverShowWidth;
  int get coverShowHeight => _coverShowHeight;
  set coverShowHeight(int coverShowHeight) =>
      _coverShowHeight = coverShowHeight;
  int get musicVideo => _musicVideo;
  set musicVideo(int musicVideo) => _musicVideo = musicVideo;
  int get draft => _draft;
  set draft(int draft) => _draft = draft;
  String get invationUrl => _invationUrl;
  set invationUrl(String invationUrl) => _invationUrl = invationUrl;
  String get mvUrl => _mvUrl;
  set mvUrl(String mvUrl) => _mvUrl = mvUrl;
  Null get weddingBoy => _weddingBoy;
  set weddingBoy(Null weddingBoy) => _weddingBoy = weddingBoy;
  Null get weddingGirl => _weddingGirl;
  set weddingGirl(Null weddingGirl) => _weddingGirl = weddingGirl;
  Null get weddingDate => _weddingDate;
  set weddingDate(Null weddingDate) => _weddingDate = weddingDate;
  Null get weddingAddress => _weddingAddress;
  set weddingAddress(Null weddingAddress) => _weddingAddress = weddingAddress;
  Null get weddingLng => _weddingLng;
  set weddingLng(Null weddingLng) => _weddingLng = weddingLng;
  Null get weddingLat => _weddingLat;
  set weddingLat(Null weddingLat) => _weddingLat = weddingLat;
  Null get themeId => _themeId;
  set themeId(Null themeId) => _themeId = themeId;
  int get invitationShowStatus => _invitationShowStatus;
  set invitationShowStatus(int invitationShowStatus) =>
      _invitationShowStatus = invitationShowStatus;
  int get invitationShowStatusNew => _invitationShowStatusNew;
  set invitationShowStatusNew(int invitationShowStatusNew) =>
      _invitationShowStatusNew = invitationShowStatusNew;
  int get canCreateAgain => _canCreateAgain;
  set canCreateAgain(int canCreateAgain) => _canCreateAgain = canCreateAgain;
  int get feedbackShowFlag => _feedbackShowFlag;
  set feedbackShowFlag(int feedbackShowFlag) =>
      _feedbackShowFlag = feedbackShowFlag;

  ListVo.fromJson(Map<String, dynamic> json) {
    _invitationId = json['invitationId'];
    _cover = json['cover'];
    _auditStatus = json['auditStatus'];
    _auditDesc = json['auditDesc'];
    _share = json['share'];
    _coverShowWidth = json['coverShowWidth'];
    _coverShowHeight = json['coverShowHeight'];
    _musicVideo = json['musicVideo'];
    _draft = json['draft'];
    _invationUrl = json['invationUrl'];
    _mvUrl = json['mvUrl'];
    _weddingBoy = json['weddingBoy'];
    _weddingGirl = json['weddingGirl'];
    _weddingDate = json['weddingDate'];
    _weddingAddress = json['weddingAddress'];
    _weddingLng = json['weddingLng'];
    _weddingLat = json['weddingLat'];
    _themeId = json['themeId'];
    _invitationShowStatus = json['invitationShowStatus'];
    _invitationShowStatusNew = json['invitationShowStatusNew'];
    _canCreateAgain = json['canCreateAgain'];
    _feedbackShowFlag = json['feedbackShowFlag'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['invitationId'] = this._invitationId;
    data['cover'] = this._cover;
    data['auditStatus'] = this._auditStatus;
    data['auditDesc'] = this._auditDesc;
    data['share'] = this._share;
    data['coverShowWidth'] = this._coverShowWidth;
    data['coverShowHeight'] = this._coverShowHeight;
    data['musicVideo'] = this._musicVideo;
    data['draft'] = this._draft;
    data['invationUrl'] = this._invationUrl;
    data['mvUrl'] = this._mvUrl;
    data['weddingBoy'] = this._weddingBoy;
    data['weddingGirl'] = this._weddingGirl;
    data['weddingDate'] = this._weddingDate;
    data['weddingAddress'] = this._weddingAddress;
    data['weddingLng'] = this._weddingLng;
    data['weddingLat'] = this._weddingLat;
    data['themeId'] = this._themeId;
    data['invitationShowStatus'] = this._invitationShowStatus;
    data['invitationShowStatusNew'] = this._invitationShowStatusNew;
    data['canCreateAgain'] = this._canCreateAgain;
    data['feedbackShowFlag'] = this._feedbackShowFlag;
    return data;
  }
}
