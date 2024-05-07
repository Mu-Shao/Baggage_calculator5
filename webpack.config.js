// webpack.config.js

const path = require('path');

module.exports = {
  entry: 'E:\\code\\software test\\Baggage_calculator\\src\\main\\resources\\static\\js\\index.js', // 入口文件
  output: {
    path: path.resolve(__dirname, 'dist'), // 输出文件夹
    filename: 'bundle.js' // 输出文件名
  },
  module: {
    rules: [
      // 添加处理 CSS 的 loader
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
      }
    ]
  }
};
