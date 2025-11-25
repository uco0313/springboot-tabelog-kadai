document.addEventListener('DOMContentLoaded', function() {
  const reservationInput = document.getElementById('reservation-datetime'); 
  if (!reservationInput) return;

  // 明日の日付の00:00:00を計算 (これ以降が選択可能)
  let tomorrow = new Date();
  tomorrow.setDate(tomorrow.getDate() + 1); 
  // 時刻部分をリセット
  tomorrow.setHours(0, 0, 0, 0); 
  
  // 3ヶ月後までを計算
  let maxDate = new Date();
  maxDate = new Date(maxDate.setMonth(maxDate.getMonth() + 3));
  
  // 現在時刻に最も近い0分か30分を計算して初期値とする
  let now = new Date();
  let currentMinute = now.getMinutes();
  let defaultMinuteValue = 0;
  
  // 30分未満なら0分に、30分以上なら30分に丸める
  if (currentMinute >= 30) {
      defaultMinuteValue = 30;
  }
  
  // 初期の日付を明日、時刻を現在の時間に丸めた値に設定
  // ただし、明日を選択したときに限りこの時刻が使用される
  let defaultTime = now.getHours() + ":" + String(defaultMinuteValue).padStart(2, '0');
  
  flatpickr(reservationInput, {
    locale: 'ja',
    enableTime: true,
    time_24hr: true,
    dateFormat: "Y/m/d H:i",
    minDate: tomorrow, 
    maxDate: maxDate,
    minuteIncrement: 30,
    
    // 【✨追加と修正✨】初期の「分」を0に設定し、30分単位の表示に揃える
    defaultMinute: 0, 
    
    // 【✨追加✨】初期表示値の分が強制的に00分になるように時刻文字列を渡す
    // 初期値を明日かつxx:00 or xx:30に設定
    defaultDate: tomorrow,
    defaultHour: now.getHours() // 初期表示の時間を現在の時間帯に合わせる
  });
});